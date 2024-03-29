{{
  ServoControl.spin

  This code contains functions for controlling all of the robot's servo motors
  including the wheels, grippers and head.   The wheel servos are assumed to be
  continuous rotation servos while the grippers and head are assumed to be
  standard servos whose position can be set directly.  All servos obtain their
  power from either the 5V regulator or directly from the power source.  By
  default, the jumper on the bottom-level board is set to supply 5V to all servos
  from the regulator.   A power switch is located under the bottom-level board
  to enable or disable power to all motors ... so if your robot is not moving,
  check this switch to make sure that it is turned on. 

  You must call the Start function once to start the cog that controls the
  servos.   You need to provide the initial values that the wheel servos need in
  order to be in their stopped position.   These values vary from servo to
  servo, so you should run ServoCalibration.spin to determine them. 
}}


CON
  '!!!WARNING!!!    DO NOT CHANGE ANY OF THESE CONSTANTS
  _clkmode = xtal1 + pll16x     ' This is required for proper timing
  _xinfreq = 5_000_000          ' This is required for proper timing
  PIN_LEFT_SERVO = 24            'PIN connected to left wheel servo
  PIN_RIGHT_SERVO = 3            'PIN connected to right wheel servo
  PIN_HEAD_PITCH_SERVO = 21       'PIN connected to head pitch servo
  PIN_HEAD_YAW_SERVO = 20         'PIN connected to head yaw servo
  PIN_LEFT_GRIPPER_SERVO = 25     'PIN connected to left gripper servo
  PIN_RIGHT_GRIPPER_SERVO = 2     'PIN connected to right gripper servo

  ' Maximum and Minimum servo ranges for the head and grippers
  ' THESE VALUES WILL VARY SLIGHTLY FROM ROBOT TO ROBOT
  LEFT_GRIPPER_MIN = 215
  LEFT_GRIPPER_MID = 170
  LEFT_GRIPPER_MAX = 140
  RIGHT_GRIPPER_MIN = 104
  RIGHT_GRIPPER_MID = 150
  RIGHT_GRIPPER_MAX = 181
  PITCH_MIN = 95
  PITCH_MID = 137
  PITCH_MAX = 170 
  YAW_MIN = 61
  YAW_MID = 146
  YAW_MAX = 225
  

VAR
  byte  headPitch               ' Pitch value for head servo              
  byte  headYaw                 ' Yaw value for head servo               
  byte  leftGripper             ' value for left gripper servo              
  byte  rightGripper            ' value for right gripper servo               
  long  stoppedLeftValue        ' servo value that represent a stopped state of left servo wheel
  long  stoppedRightValue       ' servo value that represent a stopped state of right servo wheel
  long  leftSpeed               ' current speed of left servo
  long  rightSpeed              ' current speed of right servo
  long  stack[32]               ' some stack space fof the new cog process                         
  byte  cog                     ' ID of cog that is controlling the servos
  byte  enableWheels            ' set to TRUE if wheels will be used, enables/disables pulses to servos
  byte  enableGrippers          ' set to TRUE if grippers will be used, enables/disables pulses to servos
  byte  enablePitch             ' set to TRUE if head pitch will be used, enables/disables pulses to servos
  byte  enableYaw               ' set to TRUE if head yaw will be used, enables/disables pulses to servos
  byte  leftGripperOpen
  byte  leftGripperStraight
  byte  leftGripperClosed
  byte  rightGripperOpen
  byte  rightGripperStraight
  byte  rightGripperClosed
  byte  headPitchHorizontal
  byte  headPitchUp
  byte  headPitchDown
  byte  headYawStraight
  byte  headYawLeft
  byte  headYawRight
  long  preferredLeftSpeed, preferredRightSpeed
  long  acceleration
  long  rampStack[32]                         
  byte  rampCog

PUB Start(leftServoStoppedValue, rightServoStoppedValue, useWheels, useGrippers, usePitch, useYaw)
  {{ Start a cog to control the servos.
    The parameters indicate the values that must be sent to the servos to stop them.
    These are obtained by running the ServoCalibration.spin program. }}

  ' Initially, the servos are off
  stoppedLeftValue := leftServoStoppedValue       
  stoppedRightValue := rightServoStoppedValue
  leftGripper := LEFT_GRIPPER_MID  
  rightGripper := RIGHT_GRIPPER_MID  
  headPitch := PITCH_MID  
  headYaw := YAW_MID
  enableWheels := useWheels
  enableGrippers := useGrippers
  enablePitch := usePitch
  enableYaw := useYaw

  leftGripperOpen := LEFT_GRIPPER_MAX
  leftGripperStraight := LEFT_GRIPPER_MID
  leftGripperClosed := LEFT_GRIPPER_MIN
  rightGripperOpen := RIGHT_GRIPPER_MAX
  rightGripperStraight := RIGHT_GRIPPER_MID
  rightGripperClosed := RIGHT_GRIPPER_MIN

  headPitchHorizontal := PITCH_MID
  headPitchUp := PITCH_MAX
  headPitchDown := PITCH_MIN
  headYawStraight := YAW_MID
  headYawLeft := YAW_MIN
  headYawRight := YAW_MAX
  
  leftSpeed := 0       
  rightSpeed := 0

  acceleration := 2
  preferredLeftSpeed := 0
  preferredRightSpeed := 0
         
  result := (cog := cognew(Run, @stack) + 1) > 0 AND (rampCog := cognew(UpdateSpeeds, @rampStack) + 1) > 0 'return true iff new cog has been created OK

PUB InitGrippers(leftOpen, leftStraight, leftClosed, rightOpen, rightStraight, rightClosed)
  leftGripperOpen := leftOpen
  leftGripperStraight := leftStraight
  leftGripperClosed := leftClosed
  rightGripperOpen := rightOpen
  rightGripperStraight := rightStraight
  rightGripperClosed := rightClosed

PUB InitHead(pitchHorizontal, pitchUp, pitchDown, yawStraight, yawLeft, yawRight)
  headPitchHorizontal := pitchHorizontal
  headPitchUp := pitchUp
  headPitchDown := pitchDown
  headYawStraight := yawStraight
  headYawLeft := yawLeft
  headYawRight := yawRight
  
PUB SetLeftSpeed(speed)
  {{ Set the speed of the left servo. }}
  leftSpeed := speed
  preferredLeftSpeed := speed

PUB SetPreferredLeftSpeed(speed)
  preferredLeftSpeed := speed
  
PUB SetRightSpeed(speed)
  {{ Set the speed of the right servo. }}
  rightSpeed := speed
  preferredRightSpeed := speed

PUB SetPreferredRightSpeed(speed)
  preferredRightSpeed := speed   

PUB SetSpeeds(lSpeed, rSpeed)
  {{ Set the speeds of both servos. }}
  leftSpeed := lSpeed
  rightSpeed := rSpeed
  preferredLeftSpeed := lSpeed
  preferredRightSpeed := rSpeed

PUB SetPreferredSpeeds(left, right)
  preferredLeftSpeed := left
  preferredRightSpeed := right

PUB SetHeadPitch(value)
  {{ Set the pitch of the head servo. }}
  headPitch := value
  
PUB SetHeadYaw(value)
  {{ Set the yaw of the head servo. }}
  headYaw := value

PUB SetLeftGripper(value)
  {{ Set the position of the left gripper servo. }}
  leftGripper := value
  
PUB SetRightGripper(value)
  {{ Set the position of the right gripper servo. }}
  rightGripper := value

PUB SetGrippers(left, right)
  {{ Set the position of the left and right gripper servo. }}
  leftGripper := left
  rightGripper := right

PUB OpenGrippers
  leftGripper := leftGripperOpen
  rightGripper := rightGripperOpen

PUB StraightGrippers
  leftGripper := leftGripperStraight
  rightGripper := rightGripperStraight

PUB CloseGrippers
  leftGripper := leftGripperClosed
  rightGripper := rightGripperClosed

PUB HeadLookForward
  headYaw := headYawStraight
  headPitch := headPitchHorizontal

PUB HeadLookForwardVertical
  headPitch := headPitchHorizontal

PUB HeadLookForwardHorizontal
  headYaw := headYawStraight

PUB HeadLookLeft
  headYaw := headYawLeft

PUB HeadLookRight
  headYaw := headYawRight

PUB HeadLookDown
  headPitch := headPitchDown

PUB HeadLookUp
  headPitch := headPitchUp

PUB Stop
  {{ Stop the cog that was controlling the servos. }}
  if cog > 0
    cogstop(cog-1)
    
PRI Run | i
  ' Set pin directions to output
  if(enableWheels)
    dira[PIN_LEFT_SERVO]~~                                       
    dira[PIN_RIGHT_SERVO]~~                                        
  if(enablePitch)
    dira[PIN_HEAD_PITCH_SERVO]~~ 
  if(enableYaw)
    dira[PIN_HEAD_YAW_SERVO]~~ 
  if(enableGrippers)
    dira[PIN_LEFT_GRIPPER_SERVO]~~ 
    dira[PIN_RIGHT_GRIPPER_SERVO]~~ 

  repeat
    if(enableWheels)
      MoveWheels
    if(enablePitch)
      MovePitch
    if(enableYaw)
      MoveYaw
    if(enableGrippers)
      MoveGrippers
    
    waitcnt(1600000 + cnt)             

PRI MoveWheels | clkCycles
  ' Move the left wheel
  clkCycles := ((stoppedLeftValue + leftSpeed) * 160 - 1250) #> 400           ' duration * 160 clk cycles (i.e., 160 = 2us) ' - inst. time, min cntMin
  !outa[PIN_LEFT_SERVO]                                            ' set to opposite state
  waitcnt(clkCycles + cnt)                              ' wait until clk gets there 
  !outa[PIN_LEFT_SERVO]                                            ' return to orig. state                   
  ' Cause a 20ms pause.  The 1597700 was calculated as clkCycles as follows:
      'ms:= clkfreq / 1_000                    ' Clock cycles for 1 ms
      'clkCycles := 20 * ms{-2300} #> 400      ' duration * clk cycles for ms - inst. time, min cntMin
  'waitcnt(1600000 + cnt )

  ' Move the right wheel                         
  clkCycles := ((stoppedRightValue - rightSpeed) * 160 - 1250) #> 400           ' duration * 160 clk cycles (i.e., 160 = 2us) ' - inst. time, min cntMin
  !outa[PIN_RIGHT_SERVO]                                            ' set to opposite state
  waitcnt(clkCycles + cnt)                              ' wait until clk gets there 
  !outa[PIN_RIGHT_SERVO]                                            ' return to orig. state                   
  ' Cause a 20ms pause.  The 1597700 was calculated as clkCycles as follows:
      'ms:= clkfreq / 1_000                    ' Clock cycles for 1 ms
      'clkCycles := 20 * ms{-2300} #> 400      ' duration * clk cycles for ms - inst. time, min cntMin
  'waitcnt(1600000 + cnt )                        

    
PRI MovePitch   
  outa[PIN_HEAD_PITCH_SERVO]~~                  'Set "Pin" High
  waitcnt((clkfreq/100_000)*headPitch+cnt)    'Wait for the specifed position (units = 10 microseconds)
  outa[PIN_HEAD_PITCH_SERVO]~                   'Set "Pin" Low
  'waitcnt(clkfreq/100+cnt)                    'Wait 10ms between pulses   

    
PRI MoveYaw   
  outa[PIN_HEAD_YAW_SERVO]~~                    'Set "Pin" High
  waitcnt((clkfreq/100_000)*headYaw+cnt)      'Wait for the specifed position (units = 10 microseconds)
  outa[PIN_HEAD_YAW_SERVO]~                     'Set "Pin" Low
  'waitcnt(clkfreq/100+cnt)                    'Wait 10ms between pulses   

    
PRI MoveGrippers   
  outa[PIN_LEFT_GRIPPER_SERVO]~~                'Set "Pin" High
  waitcnt((clkfreq/100_000)*leftGripper+cnt)  'Wait for the specifed position (units = 10 microseconds)
  outa[PIN_LEFT_GRIPPER_SERVO]~                 'Set "Pin" Low
  'waitcnt(clkfreq/100+cnt)                    'Wait 10ms between pulses
      
  outa[PIN_RIGHT_GRIPPER_SERVO]~~               'Set "Pin" High
  waitcnt((clkfreq/100_000)*rightGripper+cnt) 'Wait for the specifed position (units = 10 microseconds)
  outa[PIN_RIGHT_GRIPPER_SERVO]~                'Set "Pin" Low
  'waitcnt(clkfreq/100+cnt)                    'Wait 10ms between pulses

PRI UpdateSpeeds
  repeat
    ' ramp left servo
    if(preferredLeftSpeed =< 0)
      ' ramp down
      if(leftSpeed > preferredLeftSpeed)
        leftSpeed -= acceleration
      else
        leftSpeed := preferredLeftSpeed      
    elseif(preferredLeftSpeed => 0)
      ' ramp up
      if(leftSpeed < preferredLeftSpeed)
        leftSpeed += acceleration
      else
        leftSpeed := preferredLeftSpeed
     
    ' ramp right servo 
    if(preferredRightSpeed =< 0)
      ' ramp down
      if(rightSpeed > preferredRightSpeed)
        rightSpeed -= acceleration
      else
        rightSpeed := preferredRightSpeed
    elseif(preferredRightSpeed => 0)
      ' ramp up
      if(rightSpeed < preferredRightSpeed)
        rightSpeed += acceleration
      else
        rightSpeed := preferredRightSpeed

    waitcnt((clkfreq / 48) + cnt)