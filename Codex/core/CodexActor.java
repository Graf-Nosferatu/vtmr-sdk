/**
 * Handles the link from Codex to an internal game actor. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexActor extends CodexThing
{

/**
 * Gets the actor type of the thing.
 *               
 * @return      the actor type of the thing
 * <!-- 08/11/99 [YB] Original Programmer -->
*/
	public native int		GetActorType();

/**
 * Sets the actor type of the thing.
 *
 * @param       type the type to set (one of the ACTOR_TYPE_ constants)
 * @return      the old thing type of the thing, or -1 in error
 * <!-- 08/11/99 [YB] Original Programmer -->
*/
	public native int		SetActorType(int type);

/**
 * Gets the team this actor is on
 *
 * @return      the team the actor is on
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		GetActorTeam();

/**
 * Sets the team this actor is on
 *
 * @param       team the team to put the actor on
 * @return      the team the actor was on previously
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		SetActorTeam(int team);

/**
 * Gets the original team this actor was on (i.e. when it was created)
 *
 * @return      the team the actor was on when created
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		GetActorOriTeam();

/**
 * Sets the original team for this actor (use with caution)
 *
 * @param       team the original team to set
 * @return      the original team the actor was on previously
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		SetActorOriTeam(int team);

/**
 * Gets the actor flags of the actor
 *               
 * @return      the actor flags of the actor
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int		GetActorFlags();

/**
 * Gets the actor flags2 of the actor
 *               
 * @return      the actor flags of the actor
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		GetActorFlags2();

/**
 * Sets the actor flags2 of the actor
 *
 * @param       flags one or more flags to set
 * @return      the old actor flags2 of the actor, or -1 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int		SetActorFlags(int flags);

/**
 * Sets the actor flags of the actor.
 *
 * @param       flags one or more flags to set
 * @return      the old actor flags2 of the actor, or -1 in error
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		SetActorFlags2(int flags);

/**
 * Clears the actor flags of the actor.
 *
 * @param       flags one or more flags to clear
 * @return      the old actor flags of the actor, or -1 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int		ClearActorFlags(int flags);

/**
 * Clears the actor flags2 of the actor.
 *
 * @param       flags one or more flags to clear
 * @return      the old actor flags2 of the actor, or -1 in error
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		ClearActorFlags2(int flags);

/**
 * Gets the clanId of the actor
 *               
 * @return      the name of the actor's clan or some error message
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native int	GetActorClanId();

/**
 * Gets the clan of the actor
 *               
 * @return      the name of the actor's clan or some error message
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native String	GetActorClan();

/**
 * Sets the clan of the actor
 *
 * @param       name the actor clan name to set
 * @return      succes or failure
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native boolean	SetActorClan(String name);


/**
 * Gets the beckoning template from this actor's clan
 *               
 * @return      the name of the beckoning template or some error message
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native String	GetActorClanBeckoningTemplate();

/**
 * Gets the shape template from this actor's clan
 *               
 * @return      the name of the shape template or some error message
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native String	GetActorClanShapeTemplate();


/**
 * Gets the aura of the actor
 *               
 * @return      the actor's aura
 * <!-- 10/08/99 [YB] Original Programmer -->
*/
	public native String	GetActorAura();


/**
 * Sets the aura of the actor
 *               
 * @param       aura the aura to set
 * <!-- 10/08/99 [YB] Original Programmer -->
*/
	public native void	SetActorAura(String aura);

/**
 * Sets the aura of the actor
 *               
 * @param       aura the predefined aura to set (one of the ACTOR_AURA_ constants)
 * <!-- 10/08/99 [YB] Original Programmer -->
*/
	public native void	SetActorPredefinedAura(int aura);

/**
 * Gets the experience points value of the thing.
 *               
 * @return      the experience points value of the thing
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int		GetActorXPValue();

/**
 * Sets the experience points value of the thing.
 *
 * @param       xpValue experience points value to set
 * @return      the old experience points value of the thing, or -1 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int		SetActorXPValue(int xpValue);

/**
 * Gets the health of the thing.
 *               
 * @return      the health of the thing
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		GetActorHealth();

/**
 * Sets the health of the thing.
 *
 * @param       health health to set
 * @return      the old health of the thing, or -1.0 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		SetActorHealth(float health);

/**
 * Gets the maximum health of the thing.
 *               
 * @return      the maximum health of the thing
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		GetActorMaxHealth();

/**
 * Sets the maximum health of the thing.
 *
 * @param       maxHealth maximum health to set
 * @return      the old maximum health of the thing, or -1.0 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		SetActorMaxHealth(float maxHealth);

/**
 * Gets the walking speed of the thing.
 *               
 * @return      the walking speed of the thing
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		GetActorWalkSpeed();

/**
 * Sets the walking speed of the thing.
 *
 * @param       walkSpeed walking speed to set
 * @return      the old walking speed of the thing, or -1.0 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		SetActorWalkSpeed(float walkSpeed);

/**
 * Gets the running speed of the thing.
 *               
 * @return      the running speed of the thing
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		GetActorRunSpeed();

/**
 * Sets the running speed of the thing.
 *
 * @param       runSpeed running speed to set
 * @return      the old running speed of the thing, or -1.0 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		SetActorRunSpeed(float runSpeed);

/**
 * Gets one of the stats of the actor.
 *               
 * @param       statIndex the index of the stat to get (one of the ACTOR_STAT_ series)
 * @return      the requested stat or -1.0 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		GetActorStat(int statIndex);

/**
 * Sets one of the stats of the actor.
 *
 * @param       statIndex the index of the stat to get (one of the ACTOR_STAT_ series)
 * @param       statValue the value to set the stat to
 * @return      the requested stat or -1.0 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		SetActorStat(int statIndex, float statValue);

/**
 * Gets one of the base stats of the actor.
 *               
 * @param       statIndex the index of the base stat to get (one of the ACTOR_STAT_ series)
 * @return      the requested stat or -1.0 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		GetActorBaseStat(int statIndex);

/**
 * Sets one of the base stats of the actor.
 *
 * @param       statIndex the index of the stat to get (one of the ACTOR_STAT_ series)
 * @param       statValue the value to set the stat to
 * @return      the requested stat or -1.0 in error
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native float		SetActorBaseStat(int statIndex, float statValue);


/**
 * Gets one of the soaks of the actor.
 *               
 * @param       soakIndex the index of the soak to get (one of the DAMAGE_ series)
 * @return      the requested soak or -1.0 in error
 * <!-- 12/06/99 [YB] Original Programmer -->
*/
	public native float		GetActorSoak(int soakIndex);

/**
 * Sets one of the soaks of the actor.
 *
 * @param       soakIndex the index of the soak to get (one of the DAMAGE_ series)
 * @param       soakValue the value to set the soak to
 * @return      the requested soak or -1.0 in error
 * <!-- 12/06/99 [YB] Original Programmer -->
*/
	public native float		SetActorSoak(int soakIndex, float soakValue);

/**
 * Gets one of the base soaks of the actor.
 *               
 * @param       soakIndex the index of the base soak to get (one of the DAMAGE_ series)
 * @return      the requested soak or -1.0 in error
 * <!-- 12/06/99 [YB] Original Programmer -->
*/
	public native float		GetActorBaseSoak(int soakIndex);

/**
 * Sets one of the base soaks of the actor.
 *
 * @param       soakIndex the index of the soak to get (one of the DAMAGE_ series)
 * @param       soakValue the value to set the soak to
 * @return      the requested soak or -1.0 in error
 * <!-- 12/06/99 [YB] Original Programmer -->
*/
	public native float		SetActorBaseSoak(int soakIndex, float soakValue);

/**
 * Damages the actor
 *
 * @param       damageAmount the amount of damage to inflict
 * @param       damageType the type of damage to inflict (one of the DAMAGE_TYPE_ series)
 * @param       damagerGuid GUID of the thing that is inflicting the damage
 * @return      success or failure
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native boolean	DamageActor(float damageAmount, int damageType, int damagerGuid);

/**
 * Heals the actor
 *
 * @param       healAmount the amount of healing to apply
 * @return      success or failure
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native boolean	HealActor(float healAmount);

/**
 * Revives the actor
 *
 * @param       healthPct the percentage of health they should have after revive
 * @param       bloodPct the percentage of blood they should have after revive
 * @return      success or failure
 * <!-- 09/29/99 [YB] Original Programmer -->
*/
	public native boolean	ReviveActor(float healthPct, float bloodPct);

/**
 * Cancels the current action of the actor
 *
 * @return      success or failure
 * <!-- 10/08/99 [YB] Original Programmer -->
*/
	public native boolean	CancelActorAction();

/**
 * Makes the actor feed on another actor
 *
 * @param       targetGuid the guid of the target actor
 * @return      success or failure
 * <!-- 04/11/99 [YB] Original Programmer -->
*/
	public native boolean	Feed(int targetGuid);

/**
 * Makes an actor stop feeding
 *
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native void	StopFeeding();

/**
 * Sends the actor to another place.
 * 
 * @param       position the new position for the thing
 * @param       speed the speed at which to send it
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native boolean	SendActorToPos(float[] position, float speed);

/**
 * Makes this actor attack a target
 *
 * @param       targetGuid the guid of the actor to attack
 * @param       attackMode normal or special (0 or 1 (?))
 * @param       controlMod control flags
 * @return      success or failure
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native boolean	ActorActionAttack(int targetGuid, int attackMode, int controlMod);

/**
 * Makes this actor activate something
 *
 * @param       targetGuid the guid of the thing to activate
 * @param       controlMod control flags
 * @return      success or failure
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native boolean	ActorActionActivate(int targetGuid, int controlMod);

/**
 * Makes this actor pickup an item
 *
 * @param       targetGuid the guid of the item to pickup
 * @param       controlMod control flags
 * @return      success or failure
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native boolean	ActorActionPickup(int targetGuid, int controlMod);

/**
 * Makes this actor unstake a target
 *
 * @param       targetGuid the guid of the actor to unstake
 * @return      success or failure
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native boolean	ActorActionUnstake(int targetGuid);

/**
 * Makes this actor cast a discipline at an actor
 *
 * @param       targetGuid the guid of the actor to cast on
 * @param       disciplineName the name of the discipline to cast (caster must have it)
 * @return      success or failure
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native boolean	ActorActionCast(int targetGuid, String disciplineName);

/**
 * Makes this actor cast a discipline at a position
 *
 * @param       position the position to cast at
 * @param       disciplineName the name of the discipline to cast (caster must have it)
 * @return      success or failure
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native boolean	ActorActionCastPos(float[] position, String disciplineName);



// obsolete, replaced by CodexThing functions of same names
/**
 * Plays a foley mode on the actor
 *
 * @param       foleyMode the foleyMode to play
 * @return      the guid of the foley sound, or 0 in case of failure
 * <!-- 03/11/99 [YB] Original Programmer -->
*/
	public native int		PlayActorFoleyMode(int foleyMode);

/**
 * Plays a motionSet mode on the actor
 *
 * @param       motionSetMode the motionSetMode to play
 * @param       bMotion true for the motion channel, false for the action channel
 * @param       speed the speed the play the motion at
 * @return      returns the lenght of the animation in msec, -1 in error
 * <!-- 03/11/99 [YB] Original Programmer -->
*/
	public native int		PlayActorMotionSetMode(int motionSetMode, boolean bMotion, float speed);
	public native void		StopActorMotion();
	public native void		StopActorAction();
// obsolete, replaced by CodexThing functions of same names


/**
 * Makes the actor frenzy
 *
 * @return      success or failure
 * <!-- 09/16/99 [YB] Original Programmer -->
*/
	public native boolean	ActorFrenzy();

/**
 * Makes the actor unfrenzy
 *
 * @return      success or failure
 * <!-- 09/16/99 [YB] Original Programmer -->
*/
	public native boolean	ActorUnfrenzy();

/**
 * Is the actor restrained ?
 *
 * @return      Restrained or not
 * <!-- 02/08/00 [YB] Original Programmer -->
*/
	public native boolean	IsActorRestrained();

/**
 * Restrain this actor
 *
 * @return      success or failure
 * <!-- 02/08/00 [YB] Original Programmer -->
*/
	public native boolean	ActorRestrain();

/**
 * Unestrain this actor
 *
 * @return      success or failure
 * <!-- 02/08/00 [YB] Original Programmer -->
*/
	public native boolean	ActorUnrestrain();

/**
 * Still this actor (this is a special mode that watches for the actor to take an action and sends a actorunstill message at that point)
 *
 * @return      success or failure
 * <!-- 02/08/00 [YB] Original Programmer -->
*/
	public native boolean	ActorStill();


// ************************************************************************************************
//
// DISCIPLINES
//
// ************************************************************************************************

/**
 * Gets the level this actor has in this discipline
 * 
 * @param       disciplineName the name of the discipline to get the level
 * @return      the level (-1 to 4) or -2 in error or if the player hasn't got the discipline
 * <!-- 11/17/99 [YB] Original Programmer -->
*/
	public native int	GetActorDisciplineLevel(String disciplineName);

/**
 * Sets the level this actor has in this discipline, possibly adding the discipline
 * 
 * @param       disciplineName the name of the discipline to set the level
 * @param		level the level the discipline should be set to
 * @return      success or failure
 * <!-- 11/17/99 [YB] Original Programmer -->
*/
	public native boolean	SetActorDisciplineLevel(String disciplineName, int level);

/**
 * Sets the quickslot for this discipline
 * 
 * @param       disciplineName the name of the discipline to set the slot for
 * @param		slot the quick slot, from 0 to 5, or -1 to cancel the quickslot
 * @return      success or failure
 * <!-- 03/10/00 [YB] Original Programmer -->
*/
	public native boolean	SetActorDisciplineSlot(String disciplineName, int slot);



// ************************************************************************************************
//
// EFFECTS
//
// ************************************************************************************************


/**
 * Shapeshift this actor into that template's model, motionset and foley (parts of the underlying code are decidedly targetted at a wolf shift dunno how well it would work with something else...)
 * 
 * @param       templateName the name of the template to use
 * @return      success or failure
 * <!-- 12/05/99 [YB] Original Programmer -->
*/
	public native boolean	ActorShapeShift(String templateName, int flags);

/**
 * UnShapeshift this actor
 * 
 * @return      success or failure
 * <!-- 12/05/99 [YB] Original Programmer -->
*/
	public native boolean	ActorEndShapeShift();

/**
 * Is this actor shape shifted ?
 * 
 * @return      success or failure
 * <!-- 12/05/99 [YB] Original Programmer -->
*/
	public native boolean	IsActorShapeShifted();

/**
 * Override the current weapon on this actor (mostly intended for Feral Claws and the like).
 * 
 * @param       weaponTemplate the name of the weapon template to use
 * @param		bRemoveShield the function must also remove the shield
 * @param		bRemoveHands the function must also remove the hands
 * @return      success or failure
 * <!-- 04/16/99 [YB] Original Programmer -->
*/
	public native boolean	OverrideActorWeapon(String weaponTemplate, boolean bRemoveShield, boolean bRemoveHands);

/**
 * Cancel the override of the current weapon on this actor.
 * 
 * @return      success or failure
 * <!-- 04/16/99 [YB] Original Programmer -->
*/
	public native boolean	CancelOverrideActorWeapon();

/**
 * Sets null armor model (specifically for the initial scene in Prague, kinda hackish).
 * 
 * <!-- 07/16/99 [YB] Original Programmer -->
*/
	public native void		SetActorNoItemArmor(String model);

/**
 * Enable/disable this actor's armor
 *
 * @param       enable true or false
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native void		EnableActorArmor(boolean enable);

/**
 * Enable/disable this actor's weapon
 *
 * @param       enable true or false
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native void		EnableActorWeapon(boolean enable);

/**
 * Enable/disable this actor's shield
 *
 * @param       enable true or false
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native void		EnableActorShield(boolean enable);

/**
 * Adds a named effect on an actor.
 * 
 * @param       effectName the name of the effect to launch
 * @param		duration the duration of the effect to launch, 0 to make it permanent until removed
 * @param		level the level of the effect (from 0 to 5, this actually sets the value indirectly through the effects file)
 * @param		creatorGuid the guid of the effect creator
 * @param		flags some creation flags
 * @param		intParam user defined int parameter
 * @param		floatParam user defined float parameter
 * @return      the guid of the effect, or 0 in error
 * <!-- 08/18/99 [YB] Original Programmer -->
*/
	public native int		AddActorEffectByLevel(String effectName, int duration, int level, int creatorGuid, int flags, int intParam, float floatParam);

/**
 * Adds a named effect on an actor.
 * 
 * @param       effectName the name of the effect to launch
 * @param		duration the duration of the effect to launch, 0 to make it permanent until removed
 * @param		value the value of the effect to launch
 * @param		creatorGuid the guid of the effect creator
 * @param		flags some creation flags
 * @param		intParam user defined int parameter
 * @param		floatParam user defined float parameter
 * @return      the guid of the effect, or 0 in error
 * <!-- 08/18/99 [YB] Original Programmer -->
*/
	public native int		AddActorEffectByValue(String effectName, int duration, float value, int creatorGuid, int flags, int intParam, float floatParam);

/**
 * Finds an effect on an actor.
 * 
 * @param       effectGuid the guid of the effect to find
 * @return      the guid of the effect, or 0 if it cannot be found
 * <!-- 08/18/99 [YB] Original Programmer -->
*/
	public native int		FindActorEffect(int effectGuid);

/**
 * Finds an effect on an actor.
 * 
 * @param       effectName the name of the effect to find
 * @return      the guid of the effect, or 0 if it cannot be found
 * <!-- 08/18/99 [YB] Original Programmer -->
*/
	public native int		FindActorEffect(String effectName);

/**
 * Finds an effect on an actor.
 * 
 * @param       type the type of effect
 * @param       subType the subType of effect
 * @return      the guid ONE effect of type/subType (no guarantee of any order), or 0 if none can be found
 * <!-- 08/18/99 [YB] Original Programmer -->
*/
	public native int		FindActorEffect(int type, int subType);

/**
 * Removes an effect from an actor.
 * 
 * @param       effectGuid the guid of the effect to remove
 * @return      success or failure
 * <!-- 08/18/99 [YB] Original Programmer -->
*/
	public native boolean	RemoveActorEffect(int effectGuid);

/**
 * Removes an effect from an actor. Note that only the FIRST effect found with that name will be removed.
 * 
 * @param       effectName the name of the effect to remove
 * @return      success or failure
 * <!-- 08/18/99 [YB] Original Programmer -->
*/
	public native boolean	RemoveActorEffect(String effectName);


/**
 * Gets the level of this effect
 *
 * @param       effectGuid the guid of the effect
 * @return      the effect level
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		GetActorEffectLevel(int effectGuid);

/**
 * Gets the level of this effect
 *
 * @param       effectName the name of the effect
 * @return      the effect level
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		GetActorEffectLevel(String effectName);

/**
 * Gets the value of this effect
 *
 * @param       effectGuid the guid of the effect
 * @return      the effect value
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native float		GetActorEffectValue(int effectGuid);

/**
 * Gets the value of this effect
 *
 * @param       effectName the name of the effect
 * @return      the effect value
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native float		GetActorEffectValue(String effectName);

/**
 * Gets the int param of this effect
 *
 * @param       effectGuid the guid of the effect
 * @return      the effect int param
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		GetActorEffectIntParam(int effectGuid);

/**
 * Gets the int param of this effect
 *
 * @param       effectName the name of the effect
 * @return      the effect int param
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		GetActorEffectIntParam(String effectName);

/**
 * Gets the float param of this effect
 *
 * @param       effectGuid the guid of the effect
 * @return      the effect float param
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native float		GetActorEffectFloatParam(int effectGuid);

/**
 * Gets the float param of this effect
 *
 * @param       effectName the name of the effect
 * @return      the effect float param
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native float		GetActorEffectFloatParam(String effectName);

/**
 * Expands the duration of this effect
 *
 * @param       effectGuid the guid of the effect
 * @return      success or failure
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native boolean	ExpandActorEffect(int effectGuid, int duration, boolean bAppendDuration);

/**
 * Expands the duration of this effect
 *
 * @param       effectName the name of the effect
 * @return      success or failure
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native boolean	ExpandActorEffect(String effectName, int duration, boolean bAppendDuration);

/**
 * Computes the result of all the effects affecting the actor that match the passed type and subType
 * 
 * @param       type the effect type
 * @param       subType the effect subType (pass 0 if the effect has no subType)
 * @return      the resulting value
 * <!-- 08/18/99 [YB] Original Programmer -->
*/
	public native float		ComputeActorEffectsValue(int type, int subType);

/**
 * Gets the target of this actor's AI.
 * 
 * @return      the target guid, 0 if no target, -1 in error
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native int		AIGetTarget();

/**
 * Sets the actor to attack another actor
 *
 * @param       targetGuid the guid of the target actor
 * @return      success or failure
 * <!-- 04/11/99 [YB] Original Programmer -->
*/
	public native boolean	AISetTarget(int targetGuid);

/**
 * Gets the mode of this actor's AI.
 * 
 * @return      the mode (one of the AI_MODE_ constants), -1 in error
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native int		AIGetMode();

/**
 * Is the mode of this actor's AI locked ?
 * 
 * @return      the locked status of the AI mode
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native boolean	AIIsModeLocked();

/**
 * Locks this AI in its current mode
 * 
 * @return      the old locked status of the AI mode
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native boolean	AILockMode();

/**
 * Unlocks this AI's mode
 * 
 * @return      the old locked status of the AI mode
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native boolean	AIUnlockMode();

/**
 * Is this AI frenzyied ?
 * 
 * @return      true or false
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native boolean	AIIsFrenzied();

/**
 * Is this AI possessed ?
 * 
 * @return      true or false
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native boolean	AIIsPossessed();

/**
 * Is this AI afraid ? and if so, of what ?
 * 
 * @return		the guid of the cause, -1 in error, or 0 if not afraid
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native int		AIIsAfraid();

/**
 * Is this AI dazed ? and if so, because of what ?
 * 
 * @return		the guid of the cause, -1 in error, or 0 if not dazed
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native int		AIIsDazed();

/**
 * Is this AI mezmerized ? and if so, by who ?
 * 
 * @return		the guid of the cause, -1 in error, or 0 if not mesmerized
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native int		AIIsMesmerized();

/**
 * Is this AI possessing something ? and if so, what ?
 * 
 * @return		the guid of the possessed thing, -1 in error, or 0 if not possessing
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native int		AIIsPossessing();

/**
 * Is this AI a pet ? and if so, of who ?
 * 
 * @return		the guid of the pet master thing, -1 in error, or 0 if not a pet
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native int		AIIsPet();

/**
 * Returns the number of pets this actor has
 * 
 * @return		the number of pets, -1 in error
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public native int		AIGetNumPets();

/**
 * Returns the mind flags of this AI
 * 
 * @return		the mind flags, -1 in error
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native int		AIGetMindFlags();

/**
 * Gets the first item in the inventory for this actor
 *               
 * @return      the guid of the first inventory item, or 0 if none or in error
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native int		GetActorFirstInventoryItem();

/**
 * Adds an item to this player's inventory
 *              
 * @param		itemGuid the guid of the item to add to the inventory
 * @return      success or failure
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native boolean AddToActorInventory(int itemGuid);

/**
 * Gets the held item if any
 *               
 * @return      the guid of the first inventory item, or 0 if none or in error
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native int		GetActorHeldItem();

/**
 * Destroys the held item if any
 *               
 * @return      success or failure
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public native boolean	DestroyActorHeldItem();


	// ------------------------------------------------------------------------
	// NON NATIVES
	// ------------------------------------------------------------------------

	public int	AddActorEffectByLevel(String effectName, int duration, int level, int creatorGuid, int flags)
	{
		return(AddActorEffectByLevel(effectName, duration, level, creatorGuid, flags, 0, 0.0f));
	}

	public int	AddActorEffectByValue(String effectName, int duration, float value, int creatorGuid, int flags)
	{
		return(AddActorEffectByValue(effectName, duration, value, creatorGuid, flags, 0, 0.0f));
	}


	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexActor(int x)
	{
		super(x);
	}

}
