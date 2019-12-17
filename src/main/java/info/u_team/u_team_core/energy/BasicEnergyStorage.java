package info.u_team.u_team_core.energy;

import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.*;

public class BasicEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {
	
	public BasicEnergyStorage(int capacity) {
		this(capacity, capacity, capacity, 0);
	}
	
	public BasicEnergyStorage(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer, 0);
	}
	
	public BasicEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		this(capacity, maxReceive, maxExtract, 0);
	}
	
	public BasicEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}
	
	public int getEnergy() {
		return super.getEnergyStored();
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public void addEnergy(int energy) {
		this.energy += energy;
		if (this.energy > getMaxEnergyStored()) {
			this.energy = getMaxEnergyStored();
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		final CompoundNBT compound = new CompoundNBT();
		compound.putInt("energy", getEnergyStored());
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		setEnergy(compound.getInt("energy"));
	}
	
	public BufferReferenceHolder createSyncHandler() {
		return BufferReferenceHolder.createIntHolder(this::getEnergy, this::setEnergy);
	}
	
	@Deprecated
	public static void setTileEntityEnergy(TileEntity tileEntity, int energy) {
		setTileEntityEnergy(tileEntity, null, energy);
	}
	
	@Deprecated
	public static void setTileEntityEnergy(TileEntity tileEntity, Direction side, int energy) {
		tileEntity.getCapability(CapabilityEnergy.ENERGY, side).filter(handler -> handler instanceof BasicEnergyStorage).map(handler -> (BasicEnergyStorage) handler).ifPresent(handler -> handler.setEnergy(energy));
	}
	
	@Deprecated
	public static int getTileEntityEnergy(TileEntity tileEntity) {
		return getTileEntityEnergy(tileEntity, null);
	}
	
	@Deprecated
	public static int getTileEntityEnergy(TileEntity tileEntity, Direction side) {
		return tileEntity.getCapability(CapabilityEnergy.ENERGY, side).map(IEnergyStorage::getEnergyStored).orElse(0);
	}
	
}
