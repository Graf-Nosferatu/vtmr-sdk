/**
 * Handles the link from Codex to an internal game projectile. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexProjectile extends CodexThing
{

/**
 * Gets the projectile flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *               
 * @return      the projectile flags of the thing
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetProjectileFlags();

/**
 * Sets the projectile flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *
 * @param       flags one or more flags to set
 * @return      the old projectile flags of the thing, or -1 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		SetProjectileFlags(int flags);

/**
 * Clears the projectile flags of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *
 * @param       flags one or more flags to clear
 * @return      the old projectile flags of the thing, or -1 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		ClearProjectileFlags(int flags);

/**
 * Gets the projectile damage of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *               
 * @return      the projectile damage of the thing or 0 in error
 * <!-- 03/31/99 [YB] Original Programmer -->
*/
	public native int		GetProjectileDamage();

/**
 * Sets the projectile damage of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *
 * @param       damage new damage to set
 * @return      the old projectile damage of the thing, or 0 in error
 * <!-- 03/31/99 [YB] Original Programmer -->
*/
	public native int		SetProjectileDamage(int damage);

/**
 * Gets the projectile damageType of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *               
 * @return      the projectile damageType of the thing or -1 in error
 * <!-- 04/04/99 [YB] Original Programmer -->
*/
	public native int		GetProjectileDamageType();

/**
 * Sets the projectile damageType of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *
 * @param       damageType the new damageType to set (one of the DAMAGE_TYPE_ constants)
 * @return      the old projectile damageType of the thing, or -1 in error
 * <!-- 04/04/99 [YB] Original Programmer -->
*/
	public native int		SetProjectileDamageType(int damageType);

/**
 * Gets the guid of the projectile's owner.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *               
 * @return      the guid of the projectile's owner of the thing or 0 in error
 * <!-- 04/04/99 [YB] Original Programmer -->
*/
	public native int		GetProjectileOwner();

/**
 * Sets the projectile owner of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_PROJECTILE
 *
 * @param       ownerGuid new ownerGuid to set
 * @return      the old projectile owner of the thing, or 0 in error
 * <!-- 04/04/99 [YB] Original Programmer -->
*/
	public native int		SetProjectileOwner(int ownerGUID);


	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexProjectile(int x)
	{
		super(x);
	}

}