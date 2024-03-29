def main():
    path = "../src/main/resources/"
    flowers = ["allium", "azure_bluet", "blue_orchid", "cornflower", "dandelion", "lily_of_the_valley", "orange_tulip", "oxeye_daisy", "pink_tulip", "poppy", "red_tulip", "white_tulip","wither_rose","torchflower"]
    suffixes = ["", "x2", "x3", "x4", "x5"]
    modelFile = open("./model_template.json", "r")
    modelTemplate = modelFile.read()
    modelFile.close()

    blockstateFile = open("./blockstate_template.json", "r")
    blockstateTemplate = blockstateFile.read()
    blockstateFile.close()

    lootTableFile = open("./loottable_template.json", "r")
    lootTableTemplate = lootTableFile.read()
    lootTableFile.close()
    for flower in flowers:
        for i in range(1, len(suffixes)):
            file = open(path+"assets/minecraft/models/block/potted_"+flower+suffixes[i]+".json", "w")
            file.write(modelTemplate.format(suffix = suffixes[i], flower = flower))
            file.close()
        file = open(path+"assets/minecraft/blockstates/potted_"+flower+".json", "w")
        file.write(blockstateTemplate.format(flower = flower))
        file.close()
        file = open(path+"data/minecraft/loot_tables/blocks/potted_"+flower+".json", "w")
        file.write(lootTableTemplate.format(flower = flower))
        file.close()

main()

