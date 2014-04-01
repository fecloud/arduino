#/bin/bash
avrdude -pm32u4 -carduino -P/dev/ttyACM0 -b1200 -Uflash:w:Arduino_UNO_CODE.hex:a 
sleep 4s
echo "start upload"
avrdude -pm32u4 -cavr109 -P/dev/ttyACM0 -b57600 -Uflash:w:./Release/Arduino_Leonardo_CODE.hex:a 
echo "end upload"