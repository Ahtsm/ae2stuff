/*
 * Copyright (c) bdew, 2014 - 2015
 * https://github.com/bdew/ae2stuff
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.ae2stuff.machines.wireless

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.bdew.lib.block.{HasTE, SimpleBlock}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon
import net.minecraft.world.World

object BlockWireless extends SimpleBlock("Wireless", Material.iron) with HasTE[TileWireless] {
  override val TEClass = classOf[TileWireless]
  override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, meta: Int): Unit = {
    getTE(world, x, y, z).doUnlink()
    super.breakBlock(world, x, y, z, block, meta)
  }

  override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, player: EntityLivingBase, stack: ItemStack) {
    if (player.isInstanceOf[EntityPlayer])
      getTE(world, x, y, z).placingPlayer = player.asInstanceOf[EntityPlayer]
  }

  var icon_on_side: IIcon = null
  var icon_off_side: IIcon = null
  var icon_on_top: IIcon = null
  var icon_off_top: IIcon = null

  @SideOnly(Side.CLIENT)
  override def getIcon(side: Int, meta: Int): IIcon =
    if (side == 0 || side == 1) {
      if (meta > 0)
        icon_on_top
      else
        icon_off_top
    } else {
      if (meta > 0)
        icon_on_side
      else
        icon_off_side
    }

  @SideOnly(Side.CLIENT)
  override def registerBlockIcons(reg: IIconRegister): Unit = {
    icon_on_side = reg.registerIcon(modId + ":wireless/side_on")
    icon_off_side = reg.registerIcon(modId + ":wireless/side_off")
    icon_on_top = reg.registerIcon(modId + ":wireless/top_on")
    icon_off_top = reg.registerIcon(modId + ":wireless/top_off")
  }
}
