@echo off
rem because Eclipse sucks!
gradle clean setupDecompWorkspace --refresh-dependencies getAssets idea
