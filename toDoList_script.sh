#/usr/bin/bash

if [ $# -eq 0 ];
then
    echo "Unable to start the script (needed 2 arguments)"
    echo "Argument 1 = DEVICE ID"
    echo "Argument 2 = toDoList PACKAGE NAME"
elif [ $# -eq 1 ];
then
    echo "Unable to start the script (needed 1 more argument)"
    echo "Argument 2 = toDoList PACKAGE NAME"
    echo
    DEVICE_ID=$1
    adb -s $DEVICE_ID shell pm list packages | grep com.example
    echo
    echo "Enter package name of the toToList:"
    read PACKAGE_NAME
else
    DEVICE_ID=$1
    PACKAGE_NAME=$2
fi

if [ "$PACKAGE_NAME" == "com.example.todolist" ];
then
    adb -s $DEVICE_ID shell am start $PACKAGE_NAME/.MainActivity
    sleep 1.5
    for i in {1..5}
    do    
        sleep 0.5
        adb -s $DEVICE_ID shell input tap 900  2140
        sleep 0.5
        adb -s $DEVICE_ID shell input tap 550 1120
        sleep 0.5
        adb -s $DEVICE_ID shell input text "task"
        sleep 0.5
        adb -s $DEVICE_ID shell input tap 828 937
    done
else
    echo "Incorrect package name"
fi
