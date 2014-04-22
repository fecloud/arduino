#ARDUINODIR is the path to where you have installed the Arduino software.
#BOARD tells the script what type of Arduino you want to build for and upload to.
#SERIALDEV is the POSIX device name of the serial device that represents your attached Arduino.

AVR_TOOL := C:/arduino-1.0.5-r2/hardware/tools/avr
SERIALDEV := COM18
TARGET :=uno
MASTER_NK := .
SRC_DIR = src \
		src/core \
		src/core/avr-libc \
		src/SPI  \
		src/Ethernet \
		src/Ethernet/utility \
		src/Queue \
		src/task \
#		src/aJson \
#		src/aJson/utility


include $(MASTER_NK)/master.mk
