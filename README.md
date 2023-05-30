# BotFilter, but with an image in the background!
[![Maven Build](https://github.com/MrCheatEugene/BungeeCord-Mapmod/actions/workflows/maven.yml/badge.svg)](https://github.com/MrCheatEugene/BungeeCord-Mapmod/actions/workflows/maven.yml)

## Videos

Captcha+Falling check:

[![Only captcha](https://i.ytimg.com/vi/S27EbttIG-8/hqdefault.jpg)](https://youtu.be/S27EbttIG-8)

Falling check:

[![Only captcha](https://i.ytimg.com/vi/23O16oJyvl8/hqdefault.jpg)](https://youtu.be/23O16oJyvl8)

## Download
You can download .jar from the latest successful workflow.

## Changing the image
Because the project is very far from being perfect, you can change the image only by re-compiling this project. But, here's a way how to do it easily:

1. Fork this repo(not clone, fork!)
2. Go to "Actions" and enable workflows.
3. Edit file "BungeeCord-Mapmod/proxy/src/main/java/ru/leymooo/botfilter/captcha/generator/map/CraftMapCanvas.java"
4. Set `base64Image` variable's value to base64-encoded PNG, 128x128 image. Not the URL, but the base64 itself.
5. Commit
6. Go to "Actions" and wait for all jobs to finish. If some of them fails on "upload artifact", try re-running failed jobs
7. Download .jar artifact
8. Done!

In future, I'll add an easier way for changing background image. 
