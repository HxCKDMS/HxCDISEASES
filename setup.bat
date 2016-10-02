@echo off
rem because Eclipse sucks!
gradle clean setupCiWorkspace setupDecompWorkspace --refresh-dependencies getAssets eclipse idea
