/**
 * Handles the link from Codex to an internal game thing. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexThing extends Codex
{


/**
 * Sends a triggered message to the thing's script and to scripts that captured the thing
 *
 * @param       
 * @param       
 * @param       
 * @param       
 * @param       
 * <!-- 07/09/99 [YB] Original Programmer -->
*/
	public native boolean	Trigger(int triggererGUID, int triggerID, float p0, float p1, float p2, float p3);


/**
 * Creates a thing at the same location as this thing
 * 
 * @param       templateName the name of the template to use to create the new thing
 * @return      the guid of the created thing, 0 for failure
 * <!-- 04/28/99 [YB] Original Programmer -->
*/
	public native int		SpawnThing(String templateName);

/**
 * Creates a thing close to this thing, trying locations at 0, 45, -45, 90, -90, 135, -135, 180 degrees around the target thing.
 * 
 * @param       templateName the name of the template to use to create the new thing
 * @param       distance the distance from the 'target' thing (can be 0, in which case a 'good' distance will be computed)
 * @param       flags behavior flags
 * @return      the guid of the created thing, 0 for failure
 * <!-- 04/28/99 [YB] Original Programmer -->
*/
	public native int		SpawnThingNear(String templateName, float distance, int flags);


/**
 * Creates a treasure at the same location as this thing
 * 
 * @param       treasureClass the treasure class to use to create the new treasure
 * @param       bForceSuccess if FALSE, it is possible that no treasure is created if the random rolls fail
 * @return      the guid of the created thing, 0 for failure
 * <!-- 04/28/99 [YB] Original Programmer -->
*/
	public native int		SpawnTreasure(int treasureClass, boolean bForceSuccess);


/**
 * Creates a thing at the same location as this thing
 * 
 * @param       castID a castID as defined in the templates
 * @return      the guid of the thing that has said castID
 * <!-- 04/28/99 [YB] Original Programmer -->
*/
	public static native int	GuidFromCastID(String castID);



/**
 * Removes the thing from the world.
 *               
 * @return      succes or failure
 * <!-- 01/10/99 [YB] Original Programmer -->
*/
	public native boolean	Remove();

/**
 * Releases all captures on the thing.
 *
 * <!-- 01/20/99 [YB] Original Programmer -->
*/
	public native void		ReleaseAllCaptures();


/**
 * Gets the expireTime (see CodexSystem.GetGameTime()) of the thing
 *               
 * @return      the expireTime of the thing
 * <!-- 10/03/99 [YB] Original Programmer -->
*/
	public native int		GetExpireTime();


/**
 * Sets the expireTime (see CodexSystem.GetGameTime()) of the thing
 *
 * @param       expireTime the new expireTime of the thing
 * @return      the old expireTime of the thing
 * <!-- 10/03/99 [YB] Original Programmer -->
*/
	public native int		SetExpireTime(int expireTime);


/**
 * Gets the name of the thing'
 *               
 * @return      the name of the or "***ERR:Bad Thing***"
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native String	GetName();


/**
 * Gets the name of the thing's template
 *               
 * @return      the name of the template, or "***ERR:Bad Thing***"
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native String	GetTemplateName();

/**
 * Gets the thing type of the thing
 *               
 * @return      the thing type of the thing (see constants in the THING_TYPE_ series)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/

	public native int		GetThingType();

/**
 * Gets the sector GUID of the thing
 *               
 * @return      the sector GUID of the thing
 * <!-- 01/14/99 [YB] Original Programmer -->
*/
	public native int		GetSector();
	

/**
 * Gets the render type of the thing
 *               
 * @return      the render type of the thing (see constants in the THING_RENDER_ series)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetRenderType();

/**
 * Gets the move type of the thing
 *               
 * @return      the move type of the thing (see constants in the THING_MOVE_ series)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetMoveType();

/**
 * Sets the move type of the thing
 *               
 * @param       moveType the new move type of the thing
 * @return      the old move type of the thing (see constants in the THING_MOVE_ series)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		SetMoveType(int moveType);

/**
 * Gets the collide type of the thing
 *               
 * @return      the collide type of the thing (see constants in the THING_COLLIDE_ series)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetCollideType();

/**
 * Sets the collide type of the thing
 *
 * @param       collideType the new collide type (see constants in the THING_COLLIDE_ series)
 * @return      the old collide type of the thing (see constants in the THING_COLLIDE_ series)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		SetCollideType(int collideType);

/**
 * Gets the collide radius of the thing.
 * <BR>Applies only to things with CollideType == THING_COLLIDE_SPHERE or THING_COLLIDE_CYL
 *               
 * @return      the radius of the collision cylinder
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float		GetCollideRadius();

/**
 * Sets the collide radius of the thing.
 * <BR>Applies only to things with CollideType == THING_COLLIDE_SPHERE or THING_COLLIDE_CYL
 *               
 * @param       collideHeight the new height of the collision cylinder
 * @return      the old radius of the collision cylinder
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float		SetCollideRadius(float collideRadius);

/**
 * Gets the collide height of the thing.
 * <BR>Applies only to things with CollideType == THING_COLLIDE_CYL
 *               
 * @return      the height of the collision cylinder
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float		GetCollideHeight();

/**
 * Sets the collide height of the thing.
 * <BR>Applies only to things with CollideType == THING_COLLIDE_CYL
 *               
 * @param       collideHeight the new height of the collision cylinder
 * @return      the old height of the collision cylinder
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float		SetCollideHeight(float collideHeight);

	// flag verbs
/**
 * Gets the thing flags of the thing.
 *               
 * @return      the thing flags of the thing
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetThingFlags();

/**
 * Sets the thing flags of the thing.
 *
 * @param       flags one or more flags to set
 * @return      the old thing flags of the thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		SetThingFlags(int flags);

/**
 * Clears the thing flags of the thing.
 *
 * @param       flags one or more flags to clear
 * @return      the old thing flags of the thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		ClearThingFlags(int flags);

/**
 * Gets the render flags of the thing.
 *               
 * @return      the render flags of the thing
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetRenderFlags();

/**
 * Sets the render flags of the thing.
 *
 * @param       flags one or more flags to set
 * @return      the old render flags of the thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		SetRenderFlags(int flags);

/**
 * Clears the render flags of the thing.
 *
 * @param       flags one or more flags to clear
 * @return      the old render flags of the thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		ClearRenderFlags(int flags);

/**
 * Gets the physics flags of the thing.
 *               
 * @return      the physics flags of the thing
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native int		GetPhysicsFlags();

/**
 * Sets the physics flags of the thing.
 *
 * @param       flags one or more flags to set
 * @return      the old physics flags of the thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		SetPhysicsFlags(int flags);

/**
 * Clears the physics flags of the thing.
 *
 * @param       flags one or more flags to clear
 * @return      the old physics flags of the thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		ClearPhysicsFlags(int flags);

/**
 * Gets the position of the thing.
 *               
 * @return      the position of the thing
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float[]	GetPosition();

/**
 * Sets the position of the thing.
 * 
 * @param       position the new position for the thing
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native boolean	SetPosition(float[] position);

/**
 * Gets the orientation of the thing.
 *               
 * @return      the orientation of the thing (use the VEC_PITCH, VEC_YAW, VEC_ROLL constants to extract data)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float[]	GetOrientation();

/**
 * Sets the orientation of the thing.
 * 
 * @param       orientation the new orientation for the thing
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native boolean	SetOrientation(float[] orientation);

/**
 * Gets the velocity of the thing.
 *               
 * @return      the velocity of the thing (use the VEC_X, VEC_Y, VEC_Z constants to extract data)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float[]	GetVelocity();

/**
 * Sets the velocity of the thing.
 * 
 * @param       velocity the new velocity for the thing
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native boolean	SetVelocity(float[] velocity);

/**
 * Gets the rotational velocity of the thing.
 *               
 * @return      the rotational velocity of the thing (use the VEC_X, VEC_Y, VEC_Z constants to extract data)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float[]	GetRotVelocity();

/**
 * Sets the rotational velocity of the thing.
 * 
 * @param       rotational velocity the new velocity for the thing
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native boolean	SetRotVelocity(float[] velocity);

/**
 * Gets the moving status of the thing.
 *               
 * @return      TRUE if the thing is currently moving
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native boolean	IsMoving();


/**
 * Stops a thing from moving.
 *               
 * @return      success or failure
 * <!-- 03/31/99 [YB] Original Programmer -->
*/
	public native boolean	Stop();

// ************************************************************************************************
//
//	FRAMES
//
// ************************************************************************************************

/**
 * Gets the number of frames of the thing.
 *               
 * @return      the number of frames, or 0 if it is not a THING_MOVE_TRACK thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		GetNumFrames();

/**
 * Gets the current (or last if the thing is moving) frame of the thing.
 *               
 * @return      the current frame, or 0 if it is not a THING_MOVE_TRACK thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		GetCurFrame();

/**
 * Gets the goal (or current if the thing is not moving) frame of the thing.
 *               
 * @return      the goal frame, or 0 if it is not a THING_MOVE_TRACK thing
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		GetGoalFrame();

/**
 * Gets the position of the frame.
 *               
 * @param       frame the number of the frame
 * @return      the position of the frame
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float[]	GetFramePosition(int frame);

/**
 * Sets the position of the frame.
 *               
 * @param       frame the number of the frame
 * @param       position the new position of the frame
 * @return      success or failure
 * <!-- 01/13/99 [YB] Original Programmer -->
*/
	public native boolean	SetFramePosition(int frame, float[] position);

/**
 * Gets the orientation of the frame.
 *               
 * @param       frame the number of the frame
 * @return      the orientation of the frame (use the VEC_PITCH, VEC_YAW, VEC_ROLL constants to extract data)
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native float[]	GetFrameOrientation(int frame);

/**
 * Sets the orientation of the frame.
 *               
 * @param       frame the number of the frame
 * @param       orientation the new orientation of the frame
 * @return      success or failure
 * <!-- 01/13/99 [YB] Original Programmer -->
*/
	public native boolean	SetFrameOrientation(int frame, float[] orientation);

/**
 * Clears all the frames for the passed THING_MOVE_TRACK thing, and make it a THING_MOVE_NONE thing.
 *               
 * @return      success or failure
 * <!-- 01/13/99 [YB] Original Programmer -->
*/
	public native boolean	ClearFrames();

/**
 * Allocate frames for the passed THING_MOVE_NONE thing, and make it a THING_MOVE_TRACK thing with all its frames at the objects positions.
 *               
 * @param       frame the number frames to allocate
 * @return      success or failure
 * <!-- 01/13/99 [YB] Original Programmer -->
*/
	public native boolean	AllocateFrames(int numFrames);

/**
 * Moves the thing to a frame.
 *               
 * @param       frame the number of the destination frame
 * @param       speed the movement speed
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native boolean	MoveToFrame(int frame, float speed);

/**
 * Moves the thing around a frame.
 *               
 * @param       frameNum the number of the rotation frame
 * @param       duration the time to make that rotation
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public native boolean	RotatePivot(int frameNum, float duration);

// ************************************************************************************************
//
//	LINKS
//
// ************************************************************************************************


/**
 * Gets the GUID of the first thing in the same sector.
 *               
 * @return      the GUID of the first thing in the same sector, or 0 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		FirstInSector();


/**
 * Gets the GUID of the next thing in the same sector.
 *               
 * @return      the GUID of the next thing in the same sector, or 0 if no more things
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		NextInSector();

/**
 * Gets the GUID of the previous thing in the same sector.
 *               
 * @return      the GUID of the previous thing in the same sector, or 0 if no more things
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		PrevInSector();

/**
 * Gets the GUID of the first thing in the same world.
 *               
 * @return      the GUID of the first thing in the same world, or 0 in error
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		FirstInWorld();

/**
 * Gets the GUID of the next thing in the world.
 *               
 * @return      the GUID of the next thing in the world, or 0 if no more things
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		NextInWorld();

/**
 * Gets the GUID of the previous thing in the world.
 *               
 * @return      the GUID of the previous thing in the world, or 0 if no more things
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		PrevInWorld();


// ************************************************************************************************
//
// DYNALIGHT
//
// ************************************************************************************************


/**
 * Clears all the dynalight info from the thing (it frees it actually, so all the verbs working on dynalights will fail except AllocateDynamicLight).
 *               
 * @return      success or failure
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native boolean	ClearDynamicLight();

/**
 * Allocate dynalight info for the thing, setting it up with some default values.
 * 
 * @return      success or failure
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native boolean	AllocateDynamicLight();

/**
 * Gets the dynamic light intensity of the thing.
 *               
 * @return      the dynamic light intensity, 0 if not a dynalight, and -1 in error
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native float		GetDynamicLightIntensity();

/**
 * Sets the dynamic light intensity of the thing.
 *
 * @param		intensity the new intensity to set
 * @return      success or failure
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native boolean	SetDynamicLightIntensity(float intensity);

/**
 * Gets the dynamic light radius of the thing.
 *               
 * @return      the dynamic light radius, 0 if not a dynalight, and -1 in error
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native float		GetDynamicLightRadius();

/**
 * Sets the dynamic light radius of the thing.
 *
 * @param		radius the new radius to set
 * @return      success or failure
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native boolean	SetDynamicLightRadius(float radius);

/**
 * Gets the dynamic light color of the thing.
 *               
 * @return      the dynamic light color
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native float[]	GetDynamicLightColor();

/**
 * Sets the dynamic light color of the thing.
 *
 * @param		color the new color to set
 * @return      success or failure
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native boolean	SetDynamicLightColor(float[] color);

/**
 * Gets the dynamic light material (name) of the thing.
 *               
 * @return      the dynamic light material name
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native String	GetDynamicLightMaterial();

/**
 * Sets the dynamic light material of the thing.
 *
 * @param		materialName the new material to set
 * @return      success or failure
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native boolean	SetDynamicLightMaterial(String materialName);

/**
 * Gets the dynamic light style of the thing.
 *               
 * @return      the dynamic light style (one of the LIGHTSTYLE_ constants), 0 if not a dynalight, and -1 in error
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native int		GetDynamicLightStyle();

/**
 * Sets the dynamic light style of the thing.
 *
 * @param		lightStyle the new light style to set (one of the LIGHTSTYLE_ constants).
 * @return      success or failure
 * <!-- 04/05/99 [YB] Original Programmer -->
*/
	public native boolean	SetDynamicLightStyle(int lightStyle);


/**
 * Enables the thing's emitter
 *               
 * @return      success or failure
 * <!-- 12/06/99 [YB] Original Programmer -->
*/
	public native boolean	EnableEmitter();

/**
 * Disables the thing's emitter
 *               
 * @return      success or failure
 * <!-- 12/06/99 [YB] Original Programmer -->
*/
	public native boolean	DisableEmitter();



// ************************************************************************************************
//
// MISC.
//
// ************************************************************************************************


/**
 * Fires a projectile at a target thing.
 * 
 * @param       templateName the template name of the projectile to create
 * @param       targetGuid the GUID of the targetted thing
 * @param       fireOffset the offset to fire from from the thing's origin
 * @return      the GUID of the projectile, or 0 in failure
 * <!-- 03/31/99 [YB] Original Programmer -->
*/
	public native int		FireProjectileAtThing(String templateName, int targetGuid, float[] fireOffset);

/**
 * Fires a projectile at a target position.
 * 
 * @param       templateName the template name of the projectile to create
 * @param       position the position to fire at
 * @param       fireOffset the offset to fire from from the thing's origin
 * @return      the GUID of the projectile, or 0 in failure
 * <!-- 03/31/99 [YB] Original Programmer -->
*/
	public native int		FireProjectileAtPos(String templateName, float[] position, float[] fireOffset);

/**
 * Makes the thing look at a thing.
 * 
 * @param       targetGuid the GUID of the targetted thing
 * @return      success or failure
 * <!-- 03/31/99 [YB] Original Programmer -->
*/
	public native boolean	LookAtThing(int targetGuid);

/**
 * Makes the thing look at a position.
 * 
 * @param       position the position to look at
 * @return      success or failure
 * <!-- 03/31/99 [YB] Original Programmer -->
*/
	public native boolean	LookAtPos(float[] position);	


/**
 * Changes the global alpha of the thing (it actually goes through all the meshes and changes the individual alphas, so there is not "turning back" if your model had different alphas)
 * 
 * @param       alpha the new alpha value (0.0 to 1.0)
 * @param       rampTime the ramp time in seconds to go from the current alpha to the specified alpha
 * @return      success or failure
 * <!-- 01/12/00 [YB] Original Programmer -->
*/
	public native void		SetAlpha(float alpha, float rampTime);

/**
 * Changes the global scale of the thing
 * 
 * @param       scale the new scale
 * @param       rampTime the ramp time in seconds to go from the current scale to the specified scale
 * @return      success or failure
 * <!-- 01/12/00 [YB] Original Programmer -->
*/
	public native void		SetScale(float scale, float rampTime);

/**
 * Changes the scale of the thing differently on the different axis
 * 
 * @param       vecScale the new scale in x, y and z
 * @param       rampTime the ramp time in seconds to go from the current scale to the specified scale
 * @return      success or failure
 * <!-- 01/12/00 [YB] Original Programmer -->
*/
	public native void		SetScale(float[] vecScale, float rampTime);



/**
 * Sets a shell on the model of the thing
 * 
 * @param       materialName the name of the shell material
 * @param       flags flags to specify behavior
 * @param       startAlpha the starting alpha
 * @param       endAlpha the ending alpha
 * @param       rampTime the ramping time for the alpha
 * @param       scale the scale of the shell texture
 * <!-- 01/09/00 [YB] Original Programmer -->
*/
	public native void	SetShell(String materialName, int flags, float startAlpha, float endAlpha, float rampTime, float scale);

/**
 * End a shell effect on a thing model
 * 
 * <!-- 01/09/00 [YB] Original Programmer -->
*/
	public native void	EndShell();


/**
 * Attaches another thing to this thing
 * 
 * @param       attachGuid the guid of the thing to attach
 * @param       attachBone the bone to attach the thing to
 * @param       offset the offset from that bone's origin
 * @param       flags attachment flags
 * @return      success or failure
 * <!-- 04/26/99 [YB] Original Programmer -->
*/
	public native boolean	AttachThing(int attachGuid, int attachBone, float[] offset, int flags);

/**
 * Detaches another thing from this thing
 * 
 * @param       detachGuid the guid of the thing to detach
 * @return      success or failure
 * <!-- 04/26/99 [YB] Original Programmer -->
*/
	public native boolean	DetachThing(int detachGuid);


/**
 * Detaches all other things from this thing
 * 
 * @return      success or failure
 * <!-- 04/26/99 [YB] Original Programmer -->
*/
	public native boolean	DetachAllThings();

/**
 * Gets the number of things attached to this thing
 * 
 * @return      the number of things attached, -1 in error
 * <!-- 04/26/99 [YB] Original Programmer -->
*/
	public native int		GetNumAttachments();

/**
 * Gets the guid of the Nth thing attached to this thing
 * 
 * @return      the guid of the Nth thing attached, 0 in error
 * <!-- 04/26/99 [YB] Original Programmer -->
*/
	public native int		GetNthAttachment(int n);

/**
 * Gets the guid of the thing this thing is attached to.
 * 
 * @return      the guid of the thing this thing is attached to, 0 if thing is not attached, -1 in error
 * <!-- 04/26/99 [YB] Original Programmer -->
*/
	public native int		GetAttachedToThing();

/**
 * Gets the bone number of this tag
 * 
 * @param       motionTag the tag to find the bone number for (must be one of the MOTIONTAG_ constants)
 * @return      the bone number of the passed tag, or -1 in error
 * <!-- 04/27/99 [YB] Original Programmer -->
*/
	public native int		FindBone(int motionTag);

/**
 * Gets the bone offset of this tag
 * 
 * @param       motionTag the tag to find the bone number for (must be one of the MOTIONTAG_ constants)
 * @return      the bone offset of the passed tag, or (0,0,0) in error
 * <!-- 04/27/99 [YB] Original Programmer -->
*/
	public native float[]	FindBoneOffset(int motionTag);



/**
 * Sets a model on this thing. Note: the model should be compatible with the current thing's animations.
 *               
 * @param       modelName the name of the model to change to
 * @return      success or failure
 * <!-- 09/14/99 [YB] Original Programmer -->
*/
	public native boolean	SetModel(String modelName);


/**
 * Sets a foley on this thing. 
 *               
 * @param       foleyName the name of the foley to change to
 * @return      success or failure
 * <!-- 11/17/99 [YB] Original Programmer -->
*/
	public native boolean	SetFoley(String foleyName);


/**
 * Plays a foley mode on the thing
 *
 * @param       foleyMode the foleyMode to play
 * @return      the guid of the foley sound, or 0 in case of failure
 * <!-- 03/11/99 [YB] Original Programmer -->
*/
	public native int		PlayFoleyMode(int foleyMode);


/**
 * Plays a motionSet mode on the thing
 *
 * @param       motionSetMode the motionSetMode to play
 * @param       bMotion true for the motion channel, false for the action channel
 * @param       speed the speed the play the motion at
 * @return      returns the lenght of the animation in msec, -1 in error
 * <!-- 03/11/99 [YB] Original Programmer -->
*/
	public native int		PlayMotionSetMode(int motionSetMode, boolean bMotion, float speed);

	public native void		StopMotion();
	public native void		StopAction();

	public native boolean	FreezeAnimations();
	public native boolean	UnfreezeAnimations();


	public native String	GetDescriptionID();
	public native boolean	SetDescriptionID(String descriptionID);


	public native String	GetSurfaceMaterial();

	public native int		GetLocationNum();


// ************************************************************************************************
//
// EMITTER
//
// ************************************************************************************************

/**
 * Gets the emitter type of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_EMITTER
 *               
 * @return      the type of the emitter (one of the EMITTER_TYPE_ constants)
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public native int		GetEmitterType();

/**
 * Gets the emitter name of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_EMITTER
 *               
 * @return      the name of the emitter, or "***ERR:EmitterName***" in error
 * <!-- 03/29/99 [YB] Original Programmer -->
*/
	public native String	GetEmitterName();

/**
 * Gets an emitter int member of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_EMITTER
 *               
 * @param       whichMember which int member to get (one of the EMITTER_MEMBER constants)
 * @return      the int member's value, or -1 in error
 * <!-- 03/29/99 [YB] Original Programmer -->
*/
	public native int		GetEmitterIntMember(int whichMember);

/**
 * Gets an emitter float member of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_EMITTER
 *               
 * @param       whichMember which float member to get (one of the EMITTER_MEMBER constants)
 * @return      the float member's value, or -1.0 in error
 * <!-- 03/29/99 [YB] Original Programmer -->
*/
	public native float		GetEmitterFloatMember(int whichMember);

/**
 * Gets an emitter vector member of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_EMITTER
 *               
 * @param       whichMember which vector member to get (one of the EMITTER_MEMBER constants)
 * @return      the vector member's value, or (-1.0, -1.0, -1.0) in error
 * <!-- 03/29/99 [YB] Original Programmer -->
*/
	public native float[]	GetEmitterVectorMember(int whichMember);

/**
 * Sets an emitter int member of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_EMITTER
 *               
 * @param       whichMember which int member to get (one of the EMITTER_MEMBER constants)
 * @param       value the new value of the int member
 * @return      success or failure
 * <!-- 03/29/99 [YB] Original Programmer -->
*/
	public native boolean	SetEmitterIntMember(int whichMember, int value);

/**
 * Gets an emitter float member of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_EMITTER
 *               
 * @param       whichMember which float member to get (one of the EMITTER_MEMBER constants)
 * @param       value the new value of the float member
 * @return      success or failure
 * <!-- 03/29/99 [YB] Original Programmer -->
*/
	public native boolean	SetEmitterFloatMember(int whichMember, float value);

/**
 * Gets an emitter vector member of the thing.
 * <BR>Applies only to things with ThingType == THING_TYPE_EMITTER
 *               
 * @param       whichMember which vector member to get (one of the EMITTER_MEMBER constants)
 * @param       value the new value of the vector member
 * @return      success or failure
 * <!-- 03/29/99 [YB] Original Programmer -->
*/
	public native boolean	SetEmitterVectorMember(int whichMember, float[] value);


	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	public CodexThing(int x) 
	{
		if(x < 0) 
			guid = 0;
		else
			guid = x;
	}

	// ------------------------------------------------------------------------
	// NON NATIVES
	// ------------------------------------------------------------------------

/**
 * Sets the position of the thing.
 * 
 * @param       x x coord of the new position
 * @param       y y coord of the new position
 * @param       z z coord of the new position
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public boolean	SetPosition(float x, float y, float z)
	{
		float pos[] = new float[3];
		
		pos[0] = x;
		pos[1] = y;
		pos[2] = z;

		return(SetPosition(pos));
	}

/**
 * Sets the position of the thing.
 * 
 * @param       v the new position for the thing
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public boolean	SetPosition(CodexVector v)
	{
		float pos[] = new float[3];
		
		pos[0] = v.GetX();
		pos[1] = v.GetY();
		pos[2] = v.GetZ();

		return(SetPosition(pos));
	}

/**
 * Sets the orientation of the thing.
 * 
 * @param       pitch the new pitch for the thing 
 * @param       roll the new roll for the thing 
 * @param       yaw the new yaw for the thing 
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public boolean	SetOrientation(float pitch, float roll, float yaw)
	{
		float pos[] = new float[3];
		
		pos[CodexVector.VEC_PITCH] = pitch;
		pos[CodexVector.VEC_ROLL]  = roll;
		pos[CodexVector.VEC_YAW]   = yaw;

		return(SetOrientation(pos));
	}

/**
 * Sets the orientation of the thing.
 * 
 * @param       v the new orientation (pitch, roll, yaw) for the thing 
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public boolean	SetOrientation(CodexVector v)
	{
		float pos[] = new float[3];
		
		pos[CodexVector.VEC_PITCH] = v.GetX();
		pos[CodexVector.VEC_ROLL]  = v.GetY();
		pos[CodexVector.VEC_YAW]   = v.GetZ();

		return(SetOrientation(pos));
	}

/**
 * Sets the velocity of the thing.
 * 
 * @param       pitch the new pitch for the thing 
 * @param       roll the new roll for the thing 
 * @param       yaw the new yaw for the thing 
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public boolean	SetVelocity(float x, float y, float z)
	{
		float pos[] = new float[3];
		
		pos[CodexVector.VEC_X] = x;
		pos[CodexVector.VEC_Y] = y;
		pos[CodexVector.VEC_Z] = z;

		return(SetVelocity(pos));
	}

/**
 * Sets the velocity of the thing.
 * 
 * @param       v the new orientation (pitch, roll, yaw) for the thing 
 * @return      success or failure
 * <!-- 01/11/99 [YB] Original Programmer -->
*/
	public boolean	SetVelocity(CodexVector v)
	{
		float pos[] = new float[3];
		
		pos[CodexVector.VEC_X] = v.GetX();
		pos[CodexVector.VEC_Y]  = v.GetY();
		pos[CodexVector.VEC_Z]   = v.GetZ();

		return(SetVelocity(pos));
	}


/**
 * Makes the thing look at a position.
 * 
 * @param       x	x
 * @param       y	y
 * @param       z	z
 * @return      success or failure
 * <!-- 04/12/99 [YB] Original Programmer -->
*/
	public boolean	LookAtPos(float x, float y, float z)
	{
		float[] pos = new float[3];

		pos[VEC_X] = x; 
		pos[VEC_Y] = y; 
		pos[VEC_Z] = z;

		return(LookAtPos(pos));
	}

/**
 * Is the thing an actor ?
 * 
 * @return      true for an actor, else false
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public boolean	IsActor()
	{
		int type = GetThingType();
		return((type == THING_TYPE_ACTOR) || (type == THING_TYPE_PLAYER));
	}

/**
 * Is the thing a player ?
 * 
 * @return      true for a player, else false
 * <!-- 01/13/99 [YB] Original Programmer -->
*/
	public boolean	IsPlayer()
	{
		return(GetThingType() == THING_TYPE_PLAYER);
	}

/**
 * Is the thing an item ?
 * 
 * @return      true for an item, else false
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public boolean	IsItem()
	{
		int type = GetThingType();
		return((type == THING_TYPE_ITEM) || (type == THING_TYPE_WEAPON) || (type == THING_TYPE_ARMOR));
	}

/**
 * Is the thing a weapon ?
 * 
 * @return      true for a weapon, else false
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public boolean	IsWeapon()
	{
		return(GetThingType() == THING_TYPE_WEAPON);
	}

/**
 * Is the thing an armor ?
 * 
 * @return      true for an armor, else false
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public boolean	IsArmor()
	{
		return(GetThingType() == THING_TYPE_ARMOR);
	}

/**
 * Is the thing an prop ?
 * 
 * @return      true for a prop, else false
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public boolean	IsProp()
	{
		return(GetThingType() == THING_TYPE_PROP);
	}

/**
 * Is the thing a region ?
 * 
 * @return      true for a region, else false
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public boolean	IsRegion()
	{
		return(GetThingType() == THING_TYPE_REGION);
	}

/**
 * Is the thing a projectile ?
 * 
 * @return      true for a projectile, else false
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public boolean	IsProjectile()
	{
		return(GetThingType() == THING_TYPE_PROJECTILE);
	}

/**
 * Is the thing an emitter ?
 * 
 * @return      true for an emitter, else false
 * <!-- 01/12/99 [YB] Original Programmer -->
*/
	public boolean	IsEmitter()
	{
		return((GetThingFlags() & THING_FLAG_EMITTER) != 0);
	}

/**
 * Does the thing have frames ?
 * 
 * @return      true for an frame thing, else false
 * <!-- 02/11/99 [YB] Original Programmer -->
*/
	public boolean	HasFrames()
	{
		return(GetNumFrames() > 0);
	}

	public void SetAlpha(float alpha)
	{
		SetAlpha(alpha, 0.0f);
	}

	public void SetScale(float scale)
	{
		SetScale(scale, 0.0f);
	}

}
