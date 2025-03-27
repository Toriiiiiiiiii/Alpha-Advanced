package net.minecraft.src;

import java.util.Comparator;

public class EntitySorter implements Comparator {
	private Entity comparedEntity;

	public EntitySorter(Entity var1) {
		this.comparedEntity = var1;
	}

	public int sortByDistanceToEntity(WorldRenderer var1, WorldRenderer var2) {
		return var1.distanceToEntitySquared(this.comparedEntity) < var2.distanceToEntitySquared(this.comparedEntity) ? -1 : 
			(var1.distanceToEntitySquared(this.comparedEntity) == var2.distanceToEntitySquared(this.comparedEntity) ? 0 : -1);
	}

	public int compare(Object var1, Object var2) {
		return this.sortByDistanceToEntity((WorldRenderer)var1, (WorldRenderer)var2);
	}
}
