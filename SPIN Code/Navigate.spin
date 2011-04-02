{{ Navigate.spin }}

CON
  _clkmode = xtal1 + pll16x
  _xinfreq = 5_000_000
  
  ROBOT_NUMBER = 0

  LEFT_WHEEL_STOP = 742 ' NEW RANGE = 741 - 744 OLD = 744
  RIGHT_WHEEL_STOP = 744 ' NEW RANGE = 743 - 746 OLD = 746
  
  LEFT_GRIPPER_OPEN = 132
  LEFT_GRIPPER_STRAIGHT = 169
  LEFT_GRIPPER_CLOSED = 209
  
  RIGHT_GRIPPER_OPEN = 175
  RIGHT_GRIPPER_STRAIGHT = 145
  RIGHT_GRIPPER_CLOSED = 108

  PITCH_HORIZONTAL = 143
  PITCH_UP = 169
  PITCH_DOWN = 112
  
  YAW_STRAIGHT = 140
  YAW_LEFT = 60
  YAW_RIGHT = 218

  FOLLOW_LEFT = 0
  FOLLOW_RIGHT = 1

  TURN_LEFT = 0
  MOVE_FORWARD = 1
  TURN_RIGHT = 2
  STOP = 3

OBJ
  Beeper:       "Beeper"
  Servos:       "ServoControl"
  Sensors:      "IR8SensorArray"
  RBC:          "RBC"

VAR
  long seed
  long preferredLeftSpeed, preferredRightSpeed
  long actualLeftSpeed, actualRightSpeed
  long speed
  long acceleration
  long rampStart
  byte followMode, foundWall, sideBack, sideFront, frontCorner, oppositeFrontCorner, front
  byte blocked
  byte dataIn[2]
  byte rampCog
  long rampStack[32]

PUB Main
  Init

  ' run ramping on separate cog to prevent bluetooth input from stalling it
  rampCog := cognew(UpdateSpeeds, @rampStack) + 1 
  
  repeat
    Navigate

PRI Init
  RBC.Init

  Beeper.Startup

  seed := 42
  speed := 30
  acceleration := 2
  followMode := FOLLOW_LEFT
  foundWall := 0
  blocked := 0
  
  ' initialize servos
  Servos.Start(LEFT_WHEEL_STOP, RIGHT_WHEEL_STOP, true, true, true, true)
  Servos.InitGrippers(LEFT_GRIPPER_OPEN, LEFT_GRIPPER_STRAIGHT, LEFT_GRIPPER_CLOSED, RIGHT_GRIPPER_OPEN, RIGHT_GRIPPER_STRAIGHT, RIGHT_GRIPPER_CLOSED)
  Servos.InitHead(PITCH_HORIZONTAL, PITCH_UP, PITCH_DOWN, YAW_STRAIGHT, YAW_LEFT, YAW_RIGHT)
  Servos.StraightGrippers
  Servos.HeadLookForward
  waitcnt(cnt + (clkfreq / 2))
  Servos.CloseGrippers

  rampStart := cnt

  actualLeftSpeed := 0
  actualRightSpeed := 0
  preferredLeftSpeed := speed
  preferredRightSpeed := speed

  waitcnt(cnt + (clkfreq / 4))
  Beeper.Ok

PRI Navigate | inputLength, instructionID
  ' get the command from the planner
  RBC.ReceiveData(@dataIn)

  inputLength := dataIn[0]

  ' perform the corresponding instruction (and verify the array and instruction before executing)
  if(inputLength == 1)
    if(dataIn[1] == TURN_LEFT)
      preferredLeftSpeed := -6
      preferredRightSpeed := 6
      RBC.DebugStrCr(STRING("Turning Left"))
    elseif(dataIn[1] == TURN_RIGHT)
      preferredLeftSpeed := 6
      preferredRightSpeed := -6
      RBC.DebugStrCr(STRING("Turning Right"))
    elseif(dataIn[1] == MOVE_FORWARD)
      preferredLeftSpeed := 12
      preferredRightSpeed := 12
      RBC.DebugStrCr(STRING("Moving Forward"))
    elseif(dataIn[1] == STOP)
      preferredLeftSpeed := 0
      preferredRightSpeed := 0
      RBC.DebugStrCr(STRING("Stopping"))
    else
      instructionID := dataIn[1]
      RBC.DebugStr(STRING("Invalid Instruction ID (#"))
      RBC.DebugLong(instructionID)
      RBC.DebugStrCr(STRING(")"))

PRI UpdateSpeeds
  repeat
    if(cnt => rampStart + (clkfreq / 48))
      ' ramp left servo
      if(preferredLeftSpeed < 0)
        ' ramp down
        if(actualLeftSpeed > preferredLeftSpeed)
          actualLeftSpeed -= acceleration
        else
          actualLeftSpeed := preferredLeftSpeed      
      elseif(preferredLeftSpeed > 0)
        ' ramp up
        if(actualLeftSpeed < preferredLeftSpeed)
          actualLeftSpeed += acceleration
        else
          actualLeftSpeed := preferredLeftSpeed
     
      ' ramp right servo 
      if(preferredRightSpeed < 0)
        ' ramp down
        if(actualRightSpeed > preferredRightSpeed)
          actualRightSpeed -= acceleration
        else
          actualRightSpeed := preferredRightSpeed
      elseif(preferredRightSpeed > 0)
        ' ramp up
        if(actualRightSpeed < preferredRightSpeed)
          actualRightSpeed += acceleration
        else
          actualRightSpeed := preferredRightSpeed
     
      rampStart := cnt
     
      ' update servo speeds 
      Servos.SetSpeeds(actualLeftSpeed, actualRightSpeed)

PUB Random(range, minimum) : number
  number := ((|| (?seed)) // range) + minimum