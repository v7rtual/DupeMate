# DupeMate

A lightweight Minecraft Fabric mod for **1.21.9+** that helps you pentest a server for dupes

## Features

- **HUD Overlay** - Clean, non-intrusive display showing:
  - Container type
  - Block coordinates
  - Container ID
  - Item count
- **Optimized Rendering** - Custom texture atlas system for smooth performance
- **Easy Toggle** - Simple to use commands
- **50+ Methods** - Theres 50+ different methods to help you pentest your server

## Installation

1. Download and install [Fabric Loader](https://fabricmc.net/use/)
2. Download the latest release from the [Releases](../../releases) page
3. Place the `.jar` file in your `.minecraft/mods` folder
4. Launch Minecraft with the Fabric profile

## Requirements

- Minecraft 1.21.9+
- Fabric Loader 0.17.2+
- Fabric API 0.134.0+

## Usage

The HUD automatically activates when you look at a container block. The overlay appears in the top-left corner of your screen displaying relevant information about the container.

## Building from Source

```bash
git clone https://github.com/yourusername/dupemate.git
cd dupemate
./gradlew build
```

The compiled mod will be in `build/libs/`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## Credits

Built with [Fabric](https://fabricmc.net/) for Minecraft 1.21.9+
