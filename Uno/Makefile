#ARDUINODIR is the path to where you have installed the Arduino software.
#BOARD tells the script what type of Arduino you want to build for and upload to.
#SERIALDEV is the POSIX device name of the serial device that represents your attached Arduino.

AVR_TOOL := /Users/ouyangfeng/Documents/Arduino.app/Contents/Resources/Java/hardware/tools/avr
SERIALDEV := /dev/tty.usbmodemfa131
TARGET :=uno
MASTER_NK := .
SRC_DIR = src \
		src/core \
		src/core/avr-libc \
		src/SPI  \
		src/Ethernet \
		src/Ethernet/utility \
		src/websocket \
		src/Queue \
		src/task \
#		src/aJson \
#		src/aJson/utility


include $(MASTER_NK)/master.mk
