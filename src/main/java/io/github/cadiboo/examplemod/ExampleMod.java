package io.github.cadiboo.examplemod;

import io.github.cadiboo.examplemod.network.ModNetworkManager;
import io.github.cadiboo.examplemod.util.IProxy;
import io.github.cadiboo.examplemod.util.ModGuiHandler;
import io.github.cadiboo.examplemod.world.gen.ModWorldGenerator;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.cadiboo.examplemod.util.ModReference.ACCEPTED_VERSIONS;
import static io.github.cadiboo.examplemod.util.ModReference.CERTIFICATE_FINGERPRINT;
import static io.github.cadiboo.examplemod.util.ModReference.CLIENT_PROXY_CLASS;
import static io.github.cadiboo.examplemod.util.ModReference.DEPENDENCIES;
import static io.github.cadiboo.examplemod.util.ModReference.MOD_ID;
import static io.github.cadiboo.examplemod.util.ModReference.MOD_NAME;
import static io.github.cadiboo.examplemod.util.ModReference.SERVER_PROXY_CLASS;
import static io.github.cadiboo.examplemod.util.ModReference.VERSION;

/**
 * Our main mod class
 *
 * @author Cadiboo
 */
@Mod(
		modid = MOD_ID,
		name = MOD_NAME,
		version = VERSION,
		acceptedMinecraftVersions = ACCEPTED_VERSIONS,
		dependencies = DEPENDENCIES,
		certificateFingerprint = CERTIFICATE_FINGERPRINT
)
public class ExampleMod {

	public static final Logger EXAMPLE_MOD_LOG = LogManager.getLogger(MOD_ID);

	private static final Logger LOGGER = LogManager.getLogger();

	@Instance(MOD_ID)
	public static ExampleMod instance;

	@SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
	public static IProxy proxy;

	/**
	 * Run before anything else. <s>Read your config, create blocks, items, etc, and register them with the GameRegistry</s>
	 *
	 * @param event the event
	 * @see ForgeModContainer#preInit(FMLPreInitializationEvent)
	 */
	@EventHandler
	public void onPreInit(final FMLPreInitializationEvent event) {
		LOGGER.debug("preInit");
		proxy.logPhysicalSide(EXAMPLE_MOD_LOG);

		GameRegistry.registerWorldGenerator(new ModWorldGenerator(), 3);
		new ModNetworkManager();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());

		// register Capabilities if you have any

	}

	/**
	 * Do your mod setup. Build whatever data structures you care about. Register recipes, send FMLInterModComms messages to other mods.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onInit(final FMLInitializationEvent event) {
		LOGGER.debug("init");
	}

	/**
	 * Mod compatibility, or anything which depends on other mods’ init phases being finished.
	 *
	 * @param event the event
	 * @see ForgeModContainer#postInit(FMLPostInitializationEvent)
	 */
	@EventHandler
	public void onPostInit(final FMLPostInitializationEvent event) {
		LOGGER.debug("postInit");
	}

	/**
	 * Do your invalid fingerprint handling here
	 *
	 * @param event the event
	 * @see "https://tutorials.darkhax.net/tutorials/jar_signing/" and "https://mcforge.readthedocs.io/en/latest/concepts/jarsigning/"
	 */
	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
		EXAMPLE_MOD_LOG.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been tampered with. This version will NOT be supported by the author!");
	}

}
