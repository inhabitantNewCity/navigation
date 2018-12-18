cmake_minimum_required(VERSION 3.4.1)

add_library( recognition
        SHARED
        src/main/cpp/recognition.cpp)

find_library(log-lib
        log)

target_link_libraries(recognition
        ${log-lib})