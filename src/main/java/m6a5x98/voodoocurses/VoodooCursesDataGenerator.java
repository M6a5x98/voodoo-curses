package m6a5x98.voodoocurses;

import m6a5x98.voodoocurses.datagen.AdvancementProvider;
import m6a5x98.voodoocurses.datagen.ModelProvider;
import m6a5x98.voodoocurses.datagen.RecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;

public class VoodooCursesDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(RecipeProvider::new);
		pack.addProvider(ModelProvider::new);
		pack.addProvider(AdvancementProvider::new);
	}
}
