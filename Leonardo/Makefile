#ARDUINODIR is the path to where you have installed the Arduino software.
#BOARD tells the script what type of Arduino you want to build for and upload to.
#SERIALDEV is the POSIX device name of the serial device that represents your attached Arduino.

AVR_TOOL := C:/arduino-1.0.5-r2/hardware/tools/avr
SERIALDEV := /dev/ttyACM0
TARGET :=uno
MASTER_NK := .
SRC_DIR=src/core \
		src/core/avr-libc \
		src/Ethernet \
		src/Ethernet/utility \
		src/SPI src

BOARD_USB_VID 			:= 0x2341
BOARD_USB_PID 			:= 0x8036
BOARD_BUILD_MCU			:= atmega32u4

include $(MASTER_NK)/master.mk
