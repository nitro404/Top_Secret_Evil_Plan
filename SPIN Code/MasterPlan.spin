CON
  _clkmode = xtal1 + pll16x
  _xinfreq = 5_000_000
  
  ROBOT_NUMBER = 0

  LEFT_WHEEL_STOP_0 = 742
  RIGHT_WHEEL_STOP_0 = 744
  LEFT_GRIPPER_OPEN_0 = 132
  LEFT_GRIPPER_STRAIGHT_0 = 169
  LEFT_GRIPPER_CLOSED_0 = 209
  RIGHT_GRIPPER_OPEN_0 = 175
  RIGHT_GRIPPER_STRAIGHT_0 = 145
  RIGHT_GRIPPER_CLOSED_0 = 108
  PITCH_HORIZONTAL_0 = 143
  PITCH_UP_0 = 169
  PITCH_DOWN_0 = 110 '112
  YAW_STRAIGHT_0 = 140
  YAW_LEFT_0 = 60
  YAW_RIGHT_0 = 218

  LEFT_WHEEL_STOP_1 = 750
  RIGHT_WHEEL_STOP_1 = 745
  LEFT_GRIPPER_OPEN_1 = 138
  LEFT_GRIPPER_STRAIGHT_1 = 169
  LEFT_GRIPPER_CLOSED_1 = 208
  RIGHT_GRIPPER_OPEN_1 = 173
  RIGHT_GRIPPER_STRAIGHT_1 = 145
  RIGHT_GRIPPER_CLOSED_1 = 101
  PITCH_HORIZONTAL_1 = 134
  PITCH_UP_1 = 163
  PITCH_DOWN_1 = 97
  YAW_STRAIGHT_1 = 149
  YAW_LEFT_1 = 65
  YAW_RIGHT_1 = 233
  
  RED = 189
  GREEN = 21
  BLUE = 16
  SENSITIVITY = 30

  GRIPPERS_CLOSED = 0
  GRIPPERS_STRAIGHT = 1
  GRIPPERS_OPEN = 2

  NULL = 0
  STOP = 1
  MOVE_FORWARD = 2
  MOVE_FORWARD_SLOWLY = 3
  BACK_UP = 4
  BACK_UP_SLOWLY = 5
  TURN_LEFT = 6
  TURN_RIGHT = 7
  TURN_LEFT_SLOWLY = 8
  TURN_RIGHT_SLOWLY = 9
  ARC_LEFT = 10
  ARC_RIGHT = 11
  PICK_UP = 12
  DROP_OFF = 13
  DONE = 14

OBJ
  RBC:          "RBC"
  Beeper:       "Beeper"
  Servos:       "ServoControl"
  Sensors:      "IR8SensorArray"
  Block:        "BlockSensor"
  Camera:       "CMUCam"      

VAR
  long seed
  long speed
  long trackStart
  byte blockFound, blockGrabbed
  byte gripperState
  byte finished
  byte dataIn[2]

PUB Main
  Init
  
  repeat while(NOT(finished)) 
    HandleInput

PRI Init
  ' startup
  RBC.Init
  Beeper.Startup

  ' initialize local variables
  seed := 42
  speed := 20
  blockFound := 0
  blockGrabbed := 0
  gripperState := GRIPPERS_CLOSED
  finished := 0

  ' initialize camera
  Camera.Start
  Camera.SetTrackColor(RED, GREEN, BLUE, SENSITIVITY)
  RBC.SendTrackedColorToPc(RED, GREEN, BLUE)
  
  ' initialize servos
  if(ROBOT_NUMBER == 0)
    Servos.Start(LEFT_WHEEL_STOP_0, RIGHT_WHEEL_STOP_0, true, true, true, true)
    Servos.InitGrippers(LEFT_GRIPPER_OPEN_0, LEFT_GRIPPER_STRAIGHT_0, LEFT_GRIPPER_CLOSED_0, RIGHT_GRIPPER_OPEN_0, RIGHT_GRIPPER_STRAIGHT_0, RIGHT_GRIPPER_CLOSED_0)
    Servos.InitHead(PITCH_HORIZONTAL_0, PITCH_UP_0, PITCH_DOWN_0, YAW_STRAIGHT_0, YAW_LEFT_0, YAW_RIGHT_0)
  elseif(ROBOT_NUMBER == 1)
    Servos.Start(LEFT_WHEEL_STOP_1, RIGHT_WHEEL_STOP_1, true, true, true, true)
    Servos.InitGrippers(LEFT_GRIPPER_OPEN_1, LEFT_GRIPPER_STRAIGHT_1, LEFT_GRIPPER_CLOSED_1, RIGHT_GRIPPER_OPEN_1, RIGHT_GRIPPER_STRAIGHT_1, RIGHT_GRIPPER_CLOSED_1)
    Servos.InitHead(PITCH_HORIZONTAL_1, PITCH_UP_1, PITCH_DOWN_1, YAW_STRAIGHT_1, YAW_LEFT_1, YAW_RIGHT_1)
  else
    RBC.DebugStr(STRING("Missing Calibration Values for Robot #"))
    RBC.DebugLongCr(ROBOT_NUMBER)
    finished := 1
  Servos.StraightGrippers
  Servos.HeadLookForward
  waitcnt(cnt + (clkfreq / 2))
  Servos.CloseGrippers

  ' okay
  waitcnt(cnt + (clkfreq / 4))
  Beeper.Ok

  ' start timers
  trackStart := cnt

PRI HandleInput
  ' get the command from the planner
  RBC.ReceiveData(@dataIn)

  ' perform the corresponding instruction (and verify the array and instruction before executing)
  if(dataIn[0] == 1)
    if(dataIn[1] == NULL)
      ' do nothing (to prevent input from blocking)
    
    elseif(dataIn[1] == STOP)
      Servos.SetPreferredSpeeds(0, 0)
      RBC.DebugStrCr(STRING("Stopping"))
    
    elseif(dataIn[1] == MOVE_FORWARD)
      Servos.SetPreferredSpeeds(speed, speed)
      RBC.DebugStrCr(STRING("Moving Forward"))
    
    elseif(dataIn[1] == MOVE_FORWARD_SLOWLY)
      Servos.SetPreferredSpeeds(speed / 2, speed / 2)
      RBC.DebugStrCr(STRING("Moving Forward Slowly"))
    
    elseif(dataIn[1] == BACK_UP)
      Servos.SetPreferredSpeeds(-speed, -speed)
      RBC.DebugStrCr(STRING("Backing Up"))
    
    elseif(dataIn[1] == BACK_UP_SLOWLY)
      Servos.SetPreferredSpeeds(-(speed / 2), -(speed / 2))
      RBC.DebugStrCr(STRING("Backing Up Slowly"))
    
    elseif(dataIn[1] == TURN_LEFT)
      Servos.SetPreferredSpeeds(-8, 8)
      RBC.DebugStrCr(STRING("Turning Left"))
    
    elseif(dataIn[1] == TURN_RIGHT)
      Servos.SetPreferredSpeeds(8, -8)
      RBC.DebugStrCr(STRING("Turning Right"))
    
    elseif(dataIn[1] == TURN_LEFT_SLOWLY)
      Servos.SetPreferredSpeeds(-7, 7)
      RBC.DebugStrCr(STRING("Turning Left Slowly"))
    
    elseif(dataIn[1] == TURN_RIGHT_SLOWLY)
      Servos.SetPreferredSpeeds(7, -7)
      RBC.DebugStrCr(STRING("Turning Right Slowly"))
    
    elseif(dataIn[1] == ARC_LEFT)
      Servos.SetPreferredSpeeds(speed / 2, speed)
      RBC.DebugStrCr(STRING("Arcing Left"))
    
    elseif(dataIn[1] == ARC_RIGHT)
      Servos.SetPreferredSpeeds(speed, speed)
      RBC.DebugStrCr(STRING("Arcing Left"))
    
    elseif(dataIn[1] == PICK_UP)
      RBC.DebugStrCr(STRING("Finding / Picking Up Block"))
    
    elseif(dataIn[1] == DROP_OFF)
      blockFound := 0
      RBC.DebugStrCr(STRING("Dropping Off Block"))
    
    elseif(dataIn[1] == DONE)
      Servos.SetPreferredSpeeds(0, 0)
      finished := 1
      RBC.DebugStrCr(STRING("Finished!"))
    
    else
      RBC.DebugStr(STRING("Invalid Instruction ID: "))
      RBC.DebugLongCr(dataIn[1])

PRI FindBlock
  if(cnt => trackStart + (clkfreq / 4))
    Camera.TrackColor
     
    if(Camera.GetCenterX == 0 AND Camera.GetConfidence == 0)
      Servos.SetPreferredSpeeds(12, 12)
    elseif(Camera.GetCenterX > 55 AND Camera.GetConfidence > 5)
      Servos.SetPreferredSpeeds(-7, 7)
      OpenGrippers
      blockFound := 1
    elseif(Camera.GetCenterX < 15 AND Camera.GetConfidence > 5)
      Servos.SetPreferredSpeeds(7, -7)
      OpenGrippers
      blockFound := 1
    else
      Servos.SetPreferredSpeeds(12, 12)
      OpenGrippers
      blockFound := 1
     
    if(Block.Detect)
      Servos.SetPreferredSpeeds(0, 0)
      CloseGrippers
      if(NOT(blockGrabbed))
        Servos.HeadLookForwardVertical
      blockGrabbed := 1
      blockFound := 1
    
    trackStart := cnt   

PRI DeliverBlock
  Sensors.Capture

  Servos.SetPreferredSpeeds(30, 30)

  if(Sensors.Detect(3) OR Sensors.Detect(2) OR Sensors.Detect(1))
    Servos.SetPreferredSpeeds(0, 0)

    waitcnt(cnt + (clkfreq / 2))

    Servos.SetPreferredSpeeds(-20, -20)

    waitcnt(cnt + (clkfreq / 6))

    Servos.OpenGrippers
    
    waitcnt(cnt + clkfreq)

    Servos.CloseGrippers

    if(random(2, 0) == 0)
      Servos.SetPreferredSpeeds(-20, 20)
    else
      Servos.SetPreferredSpeeds(20, -20)

    waitcnt(cnt + clkfreq)

    blockFound := 0
    blockGrabbed := 0
    Servos.HeadLookDown

PRI CloseGrippers
  if(NOT(gripperState == GRIPPERS_CLOSED))
    Servos.CloseGrippers
    gripperState := GRIPPERS_CLOSED

PRI GripperStraight
  if(NOT(gripperState == GRIPPERS_STRAIGHT))
    Servos.StraightGrippers
    gripperState := GRIPPERS_STRAIGHT

PRI OpenGrippers
  if(NOT(gripperState == GRIPPERS_OPEN)) 
    Servos.OpenGrippers
    gripperState := GRIPPERS_OPEN

PUB Random(range, minimum) : number
  number := ((|| (?seed)) // range) + minimum