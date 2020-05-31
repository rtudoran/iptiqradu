# iptiqradu
The repository contains the source code for a task manager

# Readme for TaskManager task
The project addresses the tasks described in the "Task Manager.pdf" doc.

The task is implemented based on the following classes:
## package src/java/core
 - TaskManagerAPI - Defines the API for the TaskManager
 - TaskManager - Implements the TaskManager functionality

## package src/java/datamanagement
 - Process - Simple mock implementation for a Process
 - ProcessBuffer - Default implementation for buffering processes based on a time order management and overflow functionality
 - ProcessBufferFifo - Extention to the ProcessBuffer class to implement FIFO order for the process management when buffer is full
 - ProcessBufferPriority - Extention to the ProcessBuffer class to implement Priority based handling for the buffer full case

## package src/java/util
 - Config - Simple config helper file for functionality options and corresponding helper functions
 - UtilFunctions - Util class for keeping commmon functions

## others
The other 2 packages app and service layer are a simple implementation for starting as an app and using the above functionality via console. They are auxiliary to the task of the project
The test/java/* contains unit tests for the main classes to check the specified behaviour as described by the task

# Build and test
 
     mvn clean install

# Running
 run the TaskManagerApp from src/java/app package
 There is helper menu for the program params and for the console

