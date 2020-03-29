package club.sk1er.vigilance.example
//#if MC<=11202
import club.sk1er.vigilance.gui.SettingsGui
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import java.util.*
//#if MC>10809
//$$ import net.minecraft.server.MinecraftServer;
//#endif
class ExampleCommand : CommandBase() {
    //#if MC<=10809
    override fun getCommandName() = "config"

    override fun getCommandUsage(sender: ICommandSender?) = "/config - open example gui"

    override fun getRequiredPermissionLevel() = 0

    override fun processCommand(sender: ICommandSender?, args: Array<String>) {
        ExampleConfig.randomData = UUID.randomUUID().toString()
        ExampleMod.gui = SettingsGui(ExampleConfig)
    }
    //#else
    //$$ override fun getName() = "example"
    //$$
    //$$ override fun getUsage(sender: ICommandSender) = "/example - open example gui"
    //$$
    //$$ override fun getRequiredPermissionLevel() = 0
    //$$
    //$$ override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
    //$$      ExampleConfig.randomData = UUID.randomUUID().toString()
    //$$        ExampleMod.gui = SettingsGui(ExampleConfig)
    //$$ }
    //#endif
}
//#else
//$$ import com.mojang.brigadier.CommandDispatcher
//$$ import net.minecraft.command.CommandSource
//$$ import net.minecraft.command.Commands
//$$ import com.mojang.brigadier.context.CommandContext
//$$ import java.util.UUID
//$$ import club.sk1er.vigilance.gui.SettingsGui
//$$ class ExampleCommand {
//$$  fun register(dispatcher: CommandDispatcher<CommandSource?>) {
//$$      dispatcher.register(Commands.literal("example").requires { usr: CommandSource -> usr.hasPermissionLevel(0) }.executes { usr: CommandContext<CommandSource> ->
//$$          ExampleConfig.randomData = UUID.randomUUID().toString()
//$$            ExampleMod.gui = SettingsGui(ExampleConfig)
//$$            1
//$$      })
//$$  }
//$$ }
//#endif