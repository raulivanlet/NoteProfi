# NoteProfi
Java &amp; JavaFX GUI Notepad "Profesional" App

# Install Java & JavaFX

```bash
sudo apt install openjfx
```

# Add JavaFX module to path ( rerun this command after every restart)

```bash
export PATH_TO_FX=/usr/share/openjfx/lib
```

# Compile and run java FX

```bash
clear && javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml NotepadFX.java && java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml NotepadFX
```

<!-- # Compile and Run App Swing

```bash
javac Notepad.java && java Notepad
``` -->

<!-- # Run Database

docker compose down
docker compose up

# Run file HelloWorld

javac HelloWorld.java
java HelloWorld

# Run file QueExample

javac QueueExample.java
java QueueExample

# Run ExempluJDBC

javac ExempluJDBC.java
java ExempluJDBC

javac ExempluJDBC.java && java ExempluJDBC

# Run Program2

javac TestTimp.java && java TestTimp -->

