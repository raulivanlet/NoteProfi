# NoteProfi

Java &amp; JavaFX GUI Notepad "Profesional" App

### Install Java & JavaFX

```bash
sudo apt install openjfx
```

### Add JavaFX module to path ( rerun this command after every restart)

```bash
export PATH_TO_FX=/usr/share/openjfx/lib
```

### Compile and run java FX

```bash
clear && javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml NotepadFX.java && java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml NotepadFX
```

