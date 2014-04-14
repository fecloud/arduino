#
#  Author: Renzo Dani
#
#  This library is free software; you can redistribute it and/or
#  modify it under the terms of the GNU GENERAL PUBLIC LICENSE
#  License as published by the Free Software Foundation; either
#  version 2 of the License, or (at your option) any later version.
#
#  This library is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
#  See the GNU General Public License for more details.
#
#  some few parts are inspired from edam's Arduino makefile (http://ed.am/dev/make/arduino-mk)
#
#
# MANDATORY:
#    ARDUINODIR
#
# OPTIONAL:
#    BOARD
#    DIR_WORK
#    LIBRARYPATH
#
#    BOARD_UPLOAD_PROTOCOL
#    SERIALDEV
#
#    SERIALMON
#    INOFILE
#    ARDUINO_SDK_VERSION
#  
#    CPPFLAGS
#    LINKFLAGS
#    AVRDUDEFLAGS
#

#build to target dir
DIR_WORK ?= target

ifndef SRC_DIR
	$(error not set SRC_DIR)
endif

ifdef SystemRoot
	OS := windows
else
ifeq ($(shell uname), Linux)
	OS := linux
endif
endif

OBJECTS:=$(foreach dir, $(SRC_DIR), \
	$(patsubst %, $(DIR_WORK)/%.o, $(wildcard $(addprefix $(dir)/, *.c *.cpp))))



#********************************************************************************************************
# software source
ifeq ($(OS),windows)
	AVRTOOLSPATH += $(AVR_TOOL)/bin/
endif
CC              :=$(AVRTOOLSPATH)avr-gcc
CXX				:=$(AVRTOOLSPATH)avr-g++
LD 				:=$(AVRTOOLSPATH)avr-ld
AR 				:=$(AVRTOOLSPATH)avr-ar
OBJCOPY 		:=$(AVRTOOLSPATH)avr-objcopy
AVRSIZE 		:=$(AVRTOOLSPATH)avr-size

#default serial monitor
SERIALMON 	?= picocom

ifndef BOARD_BUILD_MCU
	BOARD_BUILD_MCU 		:= atmega328p
endif

ifndef BOARD_BUILD_FCPU
	BOARD_BUILD_FCPU 		:= 16000000UL
endif

ifndef BOARD_USB_VID
BOARD_USB_VID 			:= 
endif

ifndef  BOARD_USB_PID 
BOARD_USB_PID 			:= 
endif

ifndef BOARD_UPLOAD_SPEED
	BOARD_UPLOAD_SPEED		:= 115200
endif

ifndef BOARD_UPLOAD_PROTOCOL
	BOARD_UPLOAD_PROTOCOL	:= arduino
endif

ifndef ARDUINO_SDK_VERSION
	ARDUINO_SDK_VERSION     :=105
endif


# cflags
CPPFLAGS += -Os -Wall -ffunction-sections -fdata-sections -fno-exceptions
CPPFLAGS += -funsigned-char -funsigned-bitfields -fpack-struct -fshort-enums

#board flags
CPPFLAGS += -mmcu=$(BOARD_BUILD_MCU) 
CPPFLAGS += -DF_CPU=$(BOARD_BUILD_FCPU)
CPPFLAGS += -DUSB_VID=$(BOARD_USB_VID)
CPPFLAGS += -DUSB_PID=$(BOARD_USB_PID)

#version 
CPPFLAGS += -DARDUINO=$(ARDUINO_SDK_VERSION)

#includes
CPPFLAGS += -I. $(foreach dir, $(SRC_DIR),\
	$(addprefix  -I,$(dir)))


CPPDEPFLAGS = -MMD -MP -MF $(DIR_WORK)/.dep/$<.dep

LINKFLAGS += -Os -Wl,--gc-sections -mmcu=$(BOARD_BUILD_MCU)

#linux flash tools
ifeq ($(OS),linux)
AVRDUDE += avrdude
AVRDUDECONF += /etc/avrdude.conf
endif

#window flash tools
ifeq ($(OS),windows)
AVRDUDE += $(AVRTOOLSPATH)/avrdude
AVRDUDECONF += $(AVR_TOOL)/etc/avrdude.conf
endif

AVRDUDEFLAGS += $(addprefix -C , $(AVRDUDECONF)) -DV -v
AVRDUDEFLAGS += -p $(BOARD_BUILD_MCU)
ifdef SERIALDEV
AVRDUDEFLAGS += -P $(SERIALDEV)
endif
AVRDUDEFLAGS += -c $(BOARD_UPLOAD_PROTOCOL) -b $(BOARD_UPLOAD_SPEED)


#********************************************************************************************************
# RULES
#********************************************************************************************************
.PHONY:	help config boards board_info monitor upload dump clean target
# default rule
.DEFAULT_GOAL := target
#********************************************************************************************************	

help:
	@echo "Targets:"
	@echo ""
	@echo "help         : prints this help"
	@echo "config       : prints current config"
	@echo "monitor      : start your serial monitor program (default: picocom) "
	@echo "upload       : upload your code to board"
	@echo "dump         : dump code from board"
	@echo "clean        : clean project"
	@echo "target       : compile project and generate hex file"
	

config:	
	@echo "OS: $(OS)"
	@echo "TARGET: $(TARGET)"
	@echo "OBJECTS: $(OBJECTS)"
	@echo "CPPFLAGS: $(CPPFLAGS)"
	@echo "CPPDEPFLAGS: $(CPPDEPFLAGS)"
	@echo "LINKFLAGS: $(LINKFLAGS)"
	@echo ""
	@echo "AVRDUDE: $(AVRDUDE)"
	@echo "AVRDUDECONF: $(AVRDUDECONF)"

#********************************************************************************************************	

monitor:
	$(SERIALMON) $(SERIALDEV)	

#********************************************************************************************************	
upload:
	$(AVRDUDE) $(AVRDUDEFLAGS) -U flash:w:$(DIR_WORK)/$(TARGET).hex:i
	
dump:
	$(AVRDUDE) $(AVRDUDEFLAGS) -U flash:r:$(DIR_WORK)/dump.hex:r	

erase:
	$(AVRDUDE) $(AVRDUDEFLAGS) -e 	
	
#********************************************************************************************************	
clean:
	rm -rf $(DIR_WORK)		
	
	
target: $(DIR_WORK)/$(TARGET).hex

#hex
$(DIR_WORK)/$(TARGET).hex: $(DIR_WORK)/$(TARGET).elf
	$(OBJCOPY) -O ihex -R .eeprom $< $@
	$(AVRSIZE) --format=avr --mcu=$(BOARD_BUILD_MCU) $<
	
#elf	
$(DIR_WORK)/$(TARGET).elf: $(OBJECTS)
	$(CC) $(LINKFLAGS) $(OBJECTS) $(ARDUINOLIB) -lm -o $@
		
#build sources	
$(DIR_WORK)/%.c.o: %.c
	mkdir -p $(DIR_WORK)/$(dir $<)
	mkdir -p $(DIR_WORK)/.dep/$(dir $<)
	$(COMPILE.c) $(CPPDEPFLAGS) -o $@ $<

$(DIR_WORK)/%.cpp.o: %.cpp
	mkdir -p $(DIR_WORK)/$(dir $<)
	mkdir -p $(DIR_WORK)/.dep/$(dir $<)
	$(COMPILE.cpp) $(CPPDEPFLAGS) -o $@ $<

$(DIR_WORK)/%.cc.o: %.cc
	mkdir -p $(DIR_WORK)/$(dir $<)
	mkdir -p $(DIR_WORK)/.dep/$(dir $<)
	$(COMPILE.cpp) $(CPPDEPFLAGS) -o $@ $<

$(DIR_WORK)/%.C.o: %.C
	mkdir -p $(DIR_WORK)/$(dir $<)
	mkdir -p $(DIR_WORK)/.dep/$(dir $<)
	$(COMPILE.cpp) $(CPPDEPFLAGS) -o $@ $<