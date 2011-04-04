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
  
  LEFT_WHEEL_STOP_5 = 744
  RIGHT_WHEEL_STOP_5 = 745
  LEFT_GRIPPER_OPEN_5 = 128
  LEFT_GRIPPER_STRAIGHT_5 = 172
  LEFT_GRIPPER_CLOSED_5 = 205
  RIGHT_GRIPPER_OPEN_5 = 174
  RIGHT_GRIPPER_STRAIGHT_5 = 140
  RIGHT_GRIPPER_CLOSED_5 = 207
  PITCH_HORIZONTAL_5 = 136
  PITCH_UP_5 = 163
  PITCH_DOWN_5 = 103
  YAW_STRAIGHT_5 = 147
  YAW_LEFT_5 = 65
  YAW_RIGHT_5 = 233

  LEFT_WHEEL_STOP_7 = 743
  RIGHT_WHEEL_STOP_7 = 748
  LEFT_GRIPPER_OPEN_7 = 149
  LEFT_GRIPPER_STRAIGHT_7 = 205
  LEFT_GRIPPER_CLOSED_7 = 217
  RIGHT_GRIPPER_OPEN_7 = 165
  RIGHT_GRIPPER_STRAIGHT_7 = 115
  RIGHT_GRIPPER_CLOSED_7 = 106
  PITCH_HORIZONTAL_7 = 134
  PITCH_UP_7 = 163
  PITCH_DOWN_7 = 105
  YAW_STRAIGHT_7 = 141
  YAW_LEFT_7 = 65
  YAW_RIGHT_7 = 233
  
  RED = 189
  GREEN = 21
  BLUE = 16
  SENSITIVITY = 30

  GRIPPERS_CLOSED = 0
  GRIPPERS_STRAIGHT = 1
  GRIPPERS_OPEN = 2

  MODE_NORMAL = 0
  MODE_FINDBLOCK = 1
  MODE_DELIVERINGBLOCK = 2

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

  FOUND_BLOCK = 0
  GRABBED_BLOCK = 1
  BLOCK_NOT_FOUND = 2
  DROPPED_OFF_BLOCK = 3

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
  long foundBlockStart, dropOffStart
  byte robotMode
  byte blockFound, blockGrabbed
  byte gripperState
  byte finished
  byte dataIn[2]
  byte dataOut[2]

PUB Main
  Init
  
  repeat while(NOT(finished)) 
    HandleInput
    FindBlock
    DeliverBlock

  Servos.Stop

PRI Init
  ' startup
  RBC.Init
  Beeper.Startup

  ' initialize local variables
  seed := 42
  speed := 20
  robotMode := MODE_NORMAL
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
  elseif(ROBOT_NUMBER == 5)
    Servos.Start(LEFT_WHEEL_STOP_5, RIGHT_WHEEL_STOP_5, true, true, true, true)
    Servos.InitGrippers(LEFT_GRIPPER_OPEN_5, LEFT_GRIPPER_STRAIGHT_5, LEFT_GRIPPER_CLOSED_5, RIGHT_GRIPPER_OPEN_5, RIGHT_GRIPPER_STRAIGHT_5, RIGHT_GRIPPER_CLOSED_5)
    Servos.InitHead(PITCH_HORIZONTAL_5, PITCH_UP_5, PITCH_DOWN_5, YAW_STRAIGHT_5, YAW_LEFT_5, YAW_RIGHT_5)
  elseif(ROBOT_NUMBER == 7)
    Servos.Start(LEFT_WHEEL_STOP_7, RIGHT_WHEEL_STOP_7, true, true, true, true)
    Servos.InitGrippers(LEFT_GRIPPER_OPEN_7, LEFT_GRIPPER_STRAIGHT_7, LEFT_GRIPPER_CLOSED_7, RIGHT_GRIPPER_OPEN_7, RIGHT_GRIPPER_STRAIGHT_7, RIGHT_GRIPPER_CLOSED_7)
    Servos.InitHead(PITCH_HORIZONTAL_7, PITCH_UP_7, PITCH_DOWN_7, YAW_STRAIGHT_7, YAW_LEFT_7, YAW_RIGHT_7)
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
      Servos.SetPreferredSpeeds(-6, 6)
      RBC.DebugStrCr(STRING("Turning Left Slowly"))
    
    elseif(dataIn[1] == TURN_RIGHT_SLOWLY)
      Servos.SetPreferredSpeeds(6, -6)
      RBC.DebugStrCr(STRING("Turning Right Slowly"))
    
    elseif(dataIn[1] == ARC_LEFT)
      Servos.SetPreferredSpeeds(speed - 2, speed)
      RBC.DebugStrCr(STRING("Arcing Left"))
    
    elseif(dataIn[1] == ARC_RIGHT)
      Servos.SetPreferredSpeeds(speed, speed - 2)
      RBC.DebugStrCr(STRING("Arcing Left"))
    
    elseif(dataIn[1] == PICK_UP)
      robotMode := MODE_FINDBLOCK
      Servos.HeadLookDown
      foundBlockStart := cnt     
      RBC.DebugStrCr(STRING("Finding / Picking Up Block"))
    
    elseif(dataIn[1] == DROP_OFF)
      robotMode := MODE_DELIVERINGBLOCK
      dropOffStart := cnt
      RBC.DebugStrCr(STRING("Dropping Off Block"))
    
    elseif(dataIn[1] == DONE)
      Servos.SetPreferredSpeeds(0, 0)
      finished := 1
      RBC.DebugStrCr(STRING("Finished!"))
    
    else
      RBC.DebugStr(STRING("Invalid Instruction ID: "))
      RBC.DebugLongCr(dataIn[1])

PRI FindBlock
  if(robotMode == MODE_FINDBLOCK)
    if(cnt => trackStart + (clkfreq / 4))
      Camera.TrackColor
       
      if(Camera.GetCenterX == 0 AND Camera.GetConfidence == 0)
        Servos.SetPreferredSpeeds(12, 12)
      elseif(Camera.GetCenterX > 55 AND Camera.GetConfidence > 5)
        Servos.SetPreferredSpeeds(-7, 7)
        OpenGrippers
        if(NOT(blockFound))
          dataOut[0] := FOUND_BLOCK
          RBC.SendDataToPc(@dataOut, 2, RBC#OUTPUT_TO_LOG_AND_FILE)
        blockFound := 1
      elseif(Camera.GetCenterX < 15 AND Camera.GetConfidence > 5)
        Servos.SetPreferredSpeeds(7, -7)
        OpenGrippers
        if(NOT(blockFound))
          dataOut[0] := FOUND_BLOCK
          RBC.SendDataToPc(@dataOut, 2, RBC#OUTPUT_TO_LOG_AND_FILE)
        blockFound := 1
        
      if(Block.Detect AND NOT(blockGrabbed))
        Servos.SetPreferredSpeeds(0, 0)
        CloseGrippers
        if(NOT(blockGrabbed))
          Servos.HeadLookForwardVertical
        blockGrabbed := 1
        blockFound := 1
        robotMode := MODE_NORMAL
        dataOut[0] := GRABBED_BLOCK
        RBC.SendDataToPc(@dataOut, 2, RBC#OUTPUT_TO_LOG_AND_FILE)
     
      if(cnt => foundBlockStart + (3 * clkfreq) AND NOT(blockGrabbed))
        CloseGrippers
        Servos.HeadLookForwardVertical
        if(blockFound)
          blockGrabbed := 1
          dataOut[0] := GRABBED_BLOCK
          RBC.SendDataToPc(@dataOut, 2, RBC#OUTPUT_TO_LOG_AND_FILE)
        else
          dataOut[0] := BLOCK_NOT_FOUND
          RBC.SendDataToPc(@dataOut, 2, RBC#OUTPUT_TO_LOG_AND_FILE)
          blockFound := 0
          blockGrabbed := 0
        robotMode := MODE_NORMAL
           
      trackStart := cnt

PRI DeliverBlock
  if(robotMode == MODE_DELIVERINGBLOCK) 
    Servos.OpenGrippers

    Servos.SetPreferredSpeeds(-10, -10)

    waitcnt(cnt + (clkfreq / 8))

    if(cnt => dropOffStart + clkfreq)
      Servos.CloseGrippers
     
      dataOut[0] := DROPPED_OFF_BLOCK
      RBC.SendDataToPc(@dataOut, 2, RBC#OUTPUT_TO_LOG_AND_FILE)
     
      blockFound := 0
      blockGrabbed := 0
      robotMode := MODE_NORMAL

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