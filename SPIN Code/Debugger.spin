{{
  Debugger.spin

  This code contains functions for performing Debug output through the propeller pins 30 and 31
  which conect to the USB cable to the PC.   You should open up the Parallax Serial Terminal
  application to view the debug output.   You can load this onto the procesor that you want, by
  including a "Debugger.spin" object and calling Init.  However, be forewarned that this code
  should only be used on 1 of the processors, as there are problems when having more than one
  processor communicate through pins 30 and 31 at the same time.    
  
  I take no credit for the code developed here as it was extracted and slightly adjusted
  from the Debug-related functions that were in the BS2_Functions.spin object available
  on the Parallax website.  The maximum allowed baudrate (according to the "BS2_Functions.spin"
  docs) is 9600, so you should probably not change this.
}}

CON
  '!!!WARNING!!!    DO NOT CHANGE ANY OF THESE CONSTANTS
  
  _clkmode = xtal1 + pll16x     ' This is required for proper timing
  _xinfreq = 5_000_000          ' This is required for proper timing                           

  RX_PIN = 31                   ' input pin for receiving data over USB
  TX_PIN = 30                   ' output pin for sending data over USB
  BAUD_RATE = 9600              ' do not change this
  BAUD_MODE = 1                 ' non-inverted bits
  DATA_BITS = 8                 ' 8-bit data
  
  BUFFER_SIZE = 25              ' maximum number string size that can be received at any time

  
VAR
  byte  dataIn[BUFFER_SIZE+1]
  long  uS  
  

OBJ
  CONV:  "Numbers"              ' Standard library used to convert numbers to strings 

  
PUB Init
  {{ Initialize the debugger.  Call this once at the top of your program.}}
  dira[TX_PIN]~~
  outa[TX_PIN]~~
  dira[RX_PIN]~
  uS := clkfreq / 1_000_000     ' compute microsecond value

  
PUB Cr
  {{ Send a carriage return to the debug window }}
  Char(13) 

  
PUB Char(c) 
  {{ Send a character to the debug window }}
  SendChar(c)

  
PUB Str(stringPtr) 
  {{ Send a string to the debug window. }}
  SendStr(stringPtr)       


PUB StrCr(stringPtr) 
  {{ Send a string to the debug window, follwed by a CR character }}
  SendStr(stringPtr)
  Cr      


PUB GetChar: byteVal
  {{ Receives a character from the debug window }}
  byteVal := RecvChar

       
PUB GetStr(stringPtr, numChars)
  {{ Receives a string from the debug window (up to max of either numChars or BUFFER_SIZE(specified in Serialio.spin)) }}
  RecvStr(stringPtr, numChars)


PUB Dec(d) 
  {{ Send a decimal number to the debug window }}
  Str(CONV.ToStr(d, CONV#DEC))

  
PUB DecCr(d) 
  {{ Send a decimal number to the debug window, followed by a CR }}
  Str(CONV.ToStr(d, CONV#DEC))
  Cr

  
PRI RecvChar: byteVal | x, br
  { Receive a character coming in from the USB Port.  The value is returned as a single byte value. }
  br := 1_000_000 / BAUD_RATE                         ' Calculate bit rate
  waitpeq(BAUD_MODE << RX_PIN, |< RX_PIN, 0)          ' Wait for idle
  waitpne(BAUD_MODE << RX_PIN, |< RX_PIN, 0)          ' Wait for Start bit
  PauseUs(br*100/90)                                  ' Pause to be centered in 1st bit time
  byteVal := ina[RX_PIN]                              ' Read LSB
  if BAUD_MODE == 1
    repeat x from 1 to DATA_BITS-1                    ' Number of bits - 1
      PauseUs(br-70)                                  ' Wait until center of next bit
      byteVal := byteVal | (ina[RX_PIN] << x)         ' Read next bit, shift and store
  else
    repeat x from 1 to DATA_BITS-1                    ' Number of bits - 1
      PauseUs(br-70)                                  ' Wait until center of next bit
      byteVal := byteVal | ((ina[RX_PIN]^1)<< x)         ' Read next bit, shift and store 

          
PRI RecvStr(stringPtr, numChars) | ptr
  { Receive a string coming in from the USB Port. Receives up to a CR (i.e., 13) to a maximum of numChars characters or BUFFER_SIZE.}

  ptr:=0
  bytefill(@dataIn,0,numChars+1)                           ' Fill string memory with 0's (null)
  dataIn[ptr] := RecvChar                                  ' get 1st character
  ptr++                                                    ' increment pointer
  repeat while (dataIn[ptr-1] <> 13) AND (ptr < numChars) AND (ptr < BUFFER_SIZE)  ' repeat until CR was last or max chars obtained 
      dataIn[ptr] := RecvChar                              ' Store character in string
      ptr++
  dataIn[ptr] := 0                                         ' set last character to null
  byteMove(stringPtr, @dataIn, ptr+1)                      ' move into string pointer position

   
PRI SendChar(ch) | x, br
  { Sends a character out to the USB port. } 
  br := 1_000_000 / (BAUD_RATE)                         ' Determine Baud rate
  ch := ((1 << DATA_BITS ) + ch) << 2                   ' Set up string with start & stop bit
  if BAUD_MODE == 0                                     ' If mode 0, invert
    ch := !ch
  PauseUs(br * 2 )                                      ' Hold for 2 bits
  repeat x from 1 to (DATA_BITS + 2)                    ' Send each bit based on baud rate
    ch := ch >> 1
    outa[TX_PIN] := ch
    PauseUs(br - 65)
  return

        
PRI SendStr(stringPtr)
  { Send a string out to the USB port, up to the 0 string terminating character. }
  repeat strsize(stringPtr)
    SendChar(byte[stringPtr++])   ' Send each character in string

      
PRI PauseUs(duration) | clkCycles
  { Pause for the given number of micro seconds. Smallest value is 20 at clkfreq = 80Mhz.  Largest value is around 50 seconds at 80Mhz. }
  clkCycles := duration * uS #> 400       ' duration * clk cycles for us (400=Minimum waitcnt value to prevent lock-up )
                                          ' - inst. time, min cntMin 
  waitcnt(clkcycles + cnt)                ' wait until clk gets there