# EarlyStage
EarlyStage adds some features for the early game to Minecraft.

### Installation
EarlyStage is a mod built for the [Fabric Loader](https://fabricmc.net/). It requires [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) and [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) to be installed separately; all other dependencies are installed with the mod.

### License
EarlyStage is licensed under MIT.

### Datapacks
Sievable blocks can be defined in a datapack.\
Folder path: ```data\earlystage\sieve_drops\YOURFILE.json```\
Example:

```json
{
    "drops": [
        {
            "replace": false,
            "block_id": "minecraft:dirt",
            "block_drops": [
                {
                    "item_id": "minecraft:string",
                    "chance": 0.03,
                    "rolls": 2
                },
                {
                    "item_id": "minecraft:iron_nugget",
                    "chance": 0.02,
                    "rolls": 1
                }
            ]
        },  
        {
            "replace": false,
            "block_id": "minecraft:sand",
            "block_drops": [
                {
                    "item_id": "minecraft:gold_nugget",
                    "chance": 0.03,
                    "rolls": 2
                }
            ]
        }
    ]
}
```
