/**
 * Common ancestor to all the user Codex classes.
 * <BR>You'd better derive your script from it or be ready for some serious trouble... :-)
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class Codex
{
	public static final boolean TRUE					= true;
	public static final boolean FALSE					= false;

	// ------------------------------------------------------------------------
	// WORLD CONSTS
	// ------------------------------------------------------------------------

	public static final int WORLD_CURRENT				= 12345678;

	public static final int WORLD_FLAG_MOVEPAUSED		= 1;
	public static final int WORLD_FLAG_AIPAUSED			= 2;
	public static final int WORLD_FLAG_MOVESTOP			= 4;	// Stop all movement next frame, but then disable this flag (one-shot)

	// ------------------------------------------------------------------------
	// VECTOR CONSTS
	// ------------------------------------------------------------------------

	public static final int VEC_X						= 0;
	public static final int VEC_Y						= 1;
	public static final int VEC_Z						= 2;

	public static final int VEC_PITCH					= 0;
	public static final int VEC_ROLL					= 1;
	public static final int VEC_YAW						= 2;

	public static final int VEC_R						= 0;
	public static final int VEC_G						= 1;
	public static final int VEC_B						= 2;

	// ------------------------------------------------------------------------
	// THING CONSTS
	// ------------------------------------------------------------------------

	// Thing type
	public static final int THING_TYPE_ACTOR			= 0;
	public static final int THING_TYPE_ITEM				= 1;
	public static final int THING_TYPE_PROP				= 2;
	public static final int THING_TYPE_REGION			= 3;
	public static final int THING_TYPE_PROJECTILE		= 4;
	public static final int THING_TYPE_WEAPON			= 5;
	public static final int THING_TYPE_ARMOR			= 6;
	public static final int THING_TYPE_PLAYER			= 7;
	public static final int THING_TYPE_CONTAINER		= 8;
	public static final int THING_TYPE_NONE				= 9;

	// Render Type
	public static final int THING_RENDER_NONE			= 0;
	public static final int THING_RENDER_MODEL			= 1;

	// Move Type
	public static final int THING_MOVE_NONE				= 0;
	public static final int THING_MOVE_WALK				= 1;
	public static final int THING_MOVE_TRACK			= 2;
	public static final int THING_MOVE_PHYSICS			= 3;

	// Collide type
	public static final int THING_COLLIDE_NONE			= 0;
	public static final int THING_COLLIDE_SPHERE		= 1;
	public static final int THING_COLLIDE_CYL			= 2;
	public static final int THING_COLLIDE_BBOX			= 3;

	// attachment flags
	public static final int ATTACH_FLAG_AUTOREMOVE		= 1;

	// damage types
	public static final int DAMAGE_TYPE_NORMAL			= 0;
	public static final int DAMAGE_TYPE_LETHAL			= 1;
	public static final int DAMAGE_TYPE_AGGRAVATED		= 2;
	public static final int DAMAGE_TYPE_ELECTRIC		= 3;
	public static final int DAMAGE_TYPE_FIRE			= 4;
	public static final int DAMAGE_TYPE_SUN				= 5;
	public static final int DAMAGE_TYPE_FAITH			= 6;
	public static final int DAMAGE_TYPE_COLD			= 7;
	public static final int DAMAGE_TYPE_POISON			= 8;
	public static final int DAMAGE_TYPE_DISEASE			= 9;

	// actor stat indices
	public static final int ACTOR_STAT_STRENGTH			= 0;
	public static final int ACTOR_STAT_DEXTERITY		= 1;
	public static final int ACTOR_STAT_STAMINA			= 2;
	public static final int ACTOR_STAT_PERCEPTION		= 3;
	public static final int ACTOR_STAT_INTELLIGENCE		= 4;
	public static final int ACTOR_STAT_WITS				= 5;
	public static final int ACTOR_STAT_BLOOD			= 6;
	public static final int ACTOR_STAT_HUMANITY			= 7;
	public static final int ACTOR_STAT_FRENZY			= 8;
	public static final int ACTOR_STAT_GENERATION		= 9;
	public static final int ACTOR_STAT_FAITH			= 10;
	public static final int ACTOR_STAT_CHARISMA			= 11;
	public static final int ACTOR_STAT_MANIPULATION		= 12;
	public static final int ACTOR_STAT_APPEARANCE		= 13;
	public static final int ACTOR_STAT_FRENZYRATING		= 14;
	public static final int ACTOR_STAT_BLOODRATING		= 15;
	public static final int ACTOR_STAT_BLOODPOOL		= 16;
	public static final int ACTOR_STAT_MANA				= 17;
	public static final int ACTOR_STAT_MANAPOOL			= 18;


	public static final int TEAM_FIRST					= 0;
	public static final int TEAM_PLAYER_FIRST			= 0;
	public static final int TEAM_PLAYER0				= 0;
	public static final int TEAM_PLAYER1				= 1;
	public static final int TEAM_PLAYER2				= 2;
	public static final int TEAM_PLAYER3				= 3;
	public static final int TEAM_PLAYER4				= 4;
	public static final int TEAM_PLAYER5				= 5;
	public static final int TEAM_PLAYER6				= 6;
	public static final int TEAM_PLAYER7				= 7;
	public static final int TEAM_PLAYER_LAST			= 7;
	public static final int TEAM_NONE					= 8;
	public static final int TEAM_NA						= 9;						// fly peds, horse, etc.
	public static final int TEAM_PEDESTRIANS			= 10;
	public static final int TEAM_LAWMEN					= 11;
	public static final int TEAM_ENEMY_FIRST			= 12;
	public static final int TEAM_ENEMY0					= 12;
	public static final int TEAM_ENEMY1					= 13;
	public static final int TEAM_ENEMY2					= 14;
	public static final int TEAM_ENEMY3					= 15;
	public static final int TEAM_ENEMY4					= 16;
	public static final int TEAM_ENEMY5					= 17;
	public static final int TEAM_ENEMY6					= 18;
	public static final int TEAM_ENEMY7					= 19;
	public static final int TEAM_ENEMY_LAST				= 19;
	public static final int TEAM_LAST					= 19;

	// Thing Flags
	public static final int THING_FLAG_SAVENEVER		= 0x1;			// Never save this object
	public static final int THING_FLAG_SAVESUSPEND		= 0x2;			// Save this only when the game is suspended
	public static final int THING_FLAG_SAVESETMODIFIED	= 0x4;			// Save this when a level is exited, but only until the objectset changes
	public static final int THING_FLAG_SAVEALWAYS		= 0x8;			// Save this object always, even if the objectset is changed in a level
	public static final int THING_FLAG_NOREFLECTION		= 0x10;			// this object does not reflect in mirrors
	public static final int THING_FLAG_EMITTER			= 0x20;			// Object is an emitter
	public static final int THING_FLAG_NOHIGHLIGHT		= 0x40;			// Object is not highlightable
	public static final int THING_FLAG_DYNALIGHT		= 0x80;			// dynamic light on/off master switch
	public static final int THING_FLAG_NONETSYNC		= 0x100;		// object should never be synchronized over the network
	public static final int THING_FLAG_DISABLECOLLIDE	= 0x200;		// temporarily disable collisions with this thing (without changing base collide type)
	public static final int THING_FLAG_PROXIMITYTRIGGER	= 0x400;		// Thing triggers a codex message when some thing comes close to it
	public static final int THING_FLAG_STATICSHADOWS	= 0x800;		// Thing model casts shadows if placed in static layer (affects lightmap generation)
	public static final int THING_FLAG_NOREGIONTRIGGER	= 0x1000;		// Thing does not trigger regions (ghostlike..)
	public static final int THING_FLAG_VISBLOCK			= 0x2000;		// Thing does block visibility for line-of-sight queries
	public static final int THING_FLAG_CAMERABLOCK		= 0x4000;		// Thing will block the camera
	public static final int THING_FLAG_NOSCATTER		= 0x8000;		// Things spawned from this thing will not be scattered, just dropped in place
	public static final int THING_FLAG_SECRET			= 0x10000;		// Thing does not highlight unless the viewer has Auspex-type abilities
	public static final int THING_FLAG_TOPSECRET		= 0x20000;		// Thing requires a LOT of "sense level" to highlight (4+)
	public static final int THING_FLAG_SILENT			= 0x40000;		// Thing makes no sounds
	public static final int THING_FLAG_BLOCKSELECT		= 0x80000;		// Thing blocks selections if not highlighting
	public static final int THING_FLAG_TELEPORT			= 0x100000;		// Thing is a teleport object (indicates it should display its owner in rollover)
	public static final int THING_FLAG_NOSTDELETE		= 0x200000;		// Thing should not be deletable by the ST

	// (internal use)
	public static final int THING_FLAG_ANIMATING		= 0x800000;		// This object has/had at least one simAnimate animation playing
	public static final int THING_FLAG_REMOVE			= 0x1000000;	// Remove object at start of next frame (for safe removals)
	public static final int THING_FLAG_TARGETTED		= 0x2000000;	// Thing is currently targeted (for combat, pickup, activation, etc.)
	public static final int THING_FLAG_SOUNDLINK		= 0x4000000;	// Thing is being used for a sound target
	public static final int THING_FLAG_NOBLOCK			= 0x8000000;	// Thing will not block walkers but collides normally
	public static final int THING_FLAG_FINALIZED		= 0x10000000;	// This has been through its final initialization
	public static final int THING_FLAG_MODIFIED			= 0x20000000;	// Thing has been modified this frame and requires network sync (server only)
	public static final int THING_FLAG_FRAMESMODIFIED	= 0x40000000;	// Thing's frames have been modified, need to send an update
	public static final int THING_FLAG_EVERMODIFIED		= 0x80000000;	// Marked permanently if the object was ever modified since created (network support)

	// Thing Render Flags
	public static final int THING_RENDERFLAG_HALO		= 0x1;			// Draw light halos around this object
	public static final int THING_RENDERFLAG_DONTRENDER	= 0x2;			// Object rendering disabled for this object
	public static final int THING_RENDERFLAG_DROPSHADOW	= 0x4;			// Object casts a drop shadow (blobby pool)
	public static final int THING_RENDERFLAG_FULLSHADOW	= 0x8;			// Object casts a full shadow (if detail setting allows)

	// Bits for the flags field of tThingMovePath
	public static final int THING_MOVEPATH_MOVING		= 0x1;
	public static final int THING_MOVEPATH_PATHING		= 0x2;
	public static final int THING_MOVEPATH_PIVOT		= 0x4;


	// ACTOR FLAGS
	public static final int THING_AF_INVUL				= 0x1;			// Actor is invulnerable
	public static final int THING_AF_INVULTOPHYSICAL	= 0x2;			// Actor is immune to physical damage (bashing, lethat, aggravated)
	public static final int THING_AF_AIPAUSED			= 0x4;			// Actor is clickable, not attackable (shopkeepers, etc.)
	public static final int THING_AF_NOFEED				= 0x8;			// Actor cannot be fed upon
	public static final int THING_AF_NOBLEED			= 0x10;			// Actor doesn't bleed when hurt
	public static final int THING_AF_ALWAYSGIBKILL		= 0x20;			// Actor always gibs
	public static final int THING_AF_CANGIBKILL			= 0x40;			// Actor might gib kill
	public static final int THING_AF_TALKTO				= 0x80;			// Actor can be talked to
	public static final int THING_AF_PICKUP				= 0x100;		// Actor can be picked up
	public static final int THING_AF_STAKED				= 0x200;		// Actor is staked and immobilized
	public static final int THING_AF_HURTBYSILVER		= 0x400;		// Actor takes double-damage from silvered weapons
	public static final int THING_AF_FASTDRINKER		= 0x800;		// Actor is a fast blood drinker
	public static final int THING_AF_SUMMONED			= 0x1000;		// Actor has been summoned
	public static final int THING_AF_ANIMAL				= 0x2000;		// Actor is an animal
	public static final int THING_AF_HUMANITYLOSS		= 0x4000;		// Actor causes humanity loss when killed by someone susceptible to that
	public static final int THING_AF_HUMANITYTRACK		= 0x8000;		// Actor has his humanity tracked (player only)
	public static final int THING_AF_CANBEHEAD			= 0x10000;		// Actor has the proper data set for decapitation
	public static final int THING_AF_ETHEREAL			= 0x20000;		// Actor has gibs to throw.
	public static final int THING_AF_NEUTRAL			= 0x40000;		// Actor is not hostile (default action is not to attack them)
	public static final int THING_AF_AINORETURNHOME		= 0x80000;		// Actor won't return home on losing his target
	public static final int THING_AF_STARTHIDDEN		= 0x100000;		// Actor starts out hidden (AI-related)
	public static final int THING_AF_NOCORPSEROT		= 0x200000;		// Actor's corpse does not rot away
	public static final int THING_AF_NODISC				= 0x400000;		// Actor cannot use any discipline
	public static final int THING_AF_NOSHAPEDISC		= 0x800000;		// Actor cannot use any discipline that changes his shape in any way
	public static final int THING_AF_VALIDSHAPE			= 0x1000000;	// Actor can be used as a model for shapeshifting
	public static final int THING_AF_LUPINE				= 0x2000000;	// Actor is a lupine
	public static final int THING_AF_NOSKELDEATH		= 0x4000000;	// Actor never plays a skeleton death

	public static final int THING_AF_DEAD				= 0x10000000;	// (internal) Actor is dead..
	public static final int THING_AF_PARTY				= 0x20000000;	// (internal) Actor is in the current party

	// ACTOR FLAGS 2 (internal use, do NOT try to SET these values, but they might be useful info for scripts)
	public static final int THING_AF2_ISBEHEADED		= 0x1;			// (internal) Actor has lost its head
	public static final int THING_AF2_LIMPING			= 0x2;			// (internal) Actor is limping
	public static final int THING_AF2_WEAPONOVERRIDE	= 0x4;			// (internal) Actor has overridden current weapon info
	public static final int THING_AF2_CONVERSATION		= 0x8;			// (internal) Actor is involved in a conversation
	public static final int THING_AF2_USINGHANDS		= 0x10;			// (internal) Actor is current using their "cutscene" hands
	public static final int THING_AF2_WEAPONNOHANDS		= 0x20;			// (internal) Weapon in use requires removal of the hand meshes
	public static final int THING_AF2_SPEAKING			= 0x40;			// (internal) Actor is speaking currently
	public static final int THING_AF2_RESTRAINED		= 0x80;			// (internal) Actor is restrained
	public static final int THING_AF2_PERMAFRENZY		= 0x100;		// (internal) Actor is permafrenzy (Humanity lost at end of game)
	public static final int THING_AF2_SCROLLCAST		= 0x200;		// (internal) Actor is casting from a scroll


	// actor types
	public static final int ACTOR_TYPE_VAMPIRE			= 0; 
	public static final int ACTOR_TYPE_GHOUL			= 1;
	public static final int ACTOR_TYPE_HUMAN			= 2;
	public static final int ACTOR_TYPE_MONSTER			= 3;

	// actor clans ids
	public static final int ACTOR_CLAN_NONE				= 0;
	public static final int ACTOR_CLAN_BRUJAH			= 1;
	public static final int ACTOR_CLAN_CAPPADOCIAN		= 2;
	public static final int ACTOR_CLAN_GANGREL			= 3;
	public static final int ACTOR_CLAN_NOSFERATU		= 4;
	public static final int ACTOR_CLAN_TOREADOR			= 5;
	public static final int ACTOR_CLAN_ASSAMITE			= 6;
	public static final int ACTOR_CLAN_GIOVANNI			= 7;
	public static final int ACTOR_CLAN_LASOMBRA			= 8;
	public static final int ACTOR_CLAN_MALKAVIAN		= 9;
	public static final int ACTOR_CLAN_RAVNOS			= 10;
	public static final int ACTOR_CLAN_SETITE			= 11;
	public static final int ACTOR_CLAN_TREMERE			= 12;
	public static final int ACTOR_CLAN_TZIMISCE			= 13;
	public static final int ACTOR_CLAN_VENTRUE			= 14;
	public static final int ACTOR_CLAN_CAITIFF			= 15;
	public static final int ACTOR_CLAN_USERDEFINED		= 16;

	// actor auras
	public static final int ACTOR_AURA_NONE				= 0;
	public static final int ACTOR_AURA_AFRAID			= 1;
	public static final int ACTOR_AURA_AGGRESSIVE		= 2;
	public static final int ACTOR_AURA_ANGRY			= 3;
	public static final int ACTOR_AURA_BITTER			= 4;
	public static final int ACTOR_AURA_CALM				= 5;
	public static final int ACTOR_AURA_COMPASSIONATE	= 6;
	public static final int ACTOR_AURA_CONFUSED			= 7;
	public static final int ACTOR_AURA_CONSERVATIVE		= 8;
	public static final int ACTOR_AURA_DAYDREAMING		= 9;
	public static final int ACTOR_AURA_DEPRESSED		= 10;
	public static final int ACTOR_AURA_DESIROUS			= 11;
	public static final int ACTOR_AURA_DIABLERIST		= 12;
	public static final int ACTOR_AURA_DISTRUSTFUL		= 13;
	public static final int ACTOR_AURA_ENVIOUS			= 14;
	public static final int ACTOR_AURA_EXCITED			= 15;
	public static final int ACTOR_AURA_FRENZIED			= 16;
	public static final int ACTOR_AURA_GENEROUS			= 17;
	public static final int ACTOR_AURA_HAPPY			= 18;
	public static final int ACTOR_AURA_HATEFUL			= 19;
	public static final int ACTOR_AURA_IDEALISTIC		= 20;
	public static final int ACTOR_AURA_INNOCENT			= 21;
	public static final int ACTOR_AURA_LOVESTRUCK		= 22;
	public static final int ACTOR_AURA_LUSTFUL			= 23;
	public static final int ACTOR_AURA_OBSESSED			= 24;
	public static final int ACTOR_AURA_PSYCHOTIC		= 25;
	public static final int ACTOR_AURA_SAD				= 26;
	public static final int ACTOR_AURA_SPIRITUAL		= 27;
	public static final int ACTOR_AURA_SUSPICIOUS		= 28;
	public static final int ACTOR_AURA_FAERIE			= 29;
	public static final int ACTOR_AURA_GHOST			= 30;
	public static final int ACTOR_AURA_MAGICUSE			= 31;
	public static final int ACTOR_AURA_VAMPIRE			= 32;
	public static final int ACTOR_AURA_WEREBEAST		= 33;
	public static final int ACTOR_AURA_USERDEFINED		= 34;

	// ITEM FLAGS
	public static final int ITEM_FLAG_DESTROYABLE		= 0x001;		// will disappear once amount goes to 0
	public static final int ITEM_FLAG_IDENTIFIED		= 0x002;		// item has been identified
	public static final int ITEM_FLAG_PRECOMPCOST		= 0x004;		// do not add modifiers when computing cost, but use shop keepers greed
	public static final int ITEM_FLAG_UNIQUECOST		= 0x008;		// the base cost is absolute, do NOT modify it in any way
	public static final int ITEM_FLAG_NOSELL			= 0x010;		// this item cannot be sold
	public static final int ITEM_FLAG_NORANDOMMOD  		= 0x020;		// this item cannot be randomly modified by the treasure system
	public static final int ITEM_FLAG_NOIDENTIFY  		= 0x040;		// this item cannot be identified (it has modifiers that we do NOT want to reveal in any case)
	public static final int ITEM_FLAG_INSTANTCASH 		= 0x080;		// this item is instant cash for the amount specified (pickup does NOT create an item in the inventory)
	public static final int ITEM_FLAG_NOSMALL			= 0x100;		// Can't be worn by small actors (OBSOLETE)
	public static final int ITEM_FLAG_NOMEDIUM			= 0x200;		// Can't be worn by medium actors (OBSOLETE)
	public static final int ITEM_FLAG_NOLARGE			= 0x400;		// Can't be worn by large actors (OBSOLETE)
	public static final int ITEM_FLAG_CARRYSPECIAL		= 0x800;		// This item forces the player to change his animations while carrying it...
	public static final int ITEM_FLAG_MAGIC				= 0x1000;		// Item should display as magical
	public static final int ITEM_FLAG_CARRYTOMODERNDAY	= 0x2000;		// Item should be retained when transitioning to modern-day
	public static final int ITEM_FLAG_CURSED			= 0x4000;		// Item should identify as cursed
	public static final int ITEM_FLAG_TWOHANDED			= 0x8000;		// Item is two handed

	// (internal use)
	public static final int ITEM_FLAG_DISABLED			= 0x100000;		// item has 0 charges, or character is unable to use item (internal)
	public static final int ITEM_FLAG_BEINGWORN			= 0x200000;		// item is currently being worn/wielded (internal)


	// REGION FLAGS
	public static final int REGION_NOTRACKING			= 0x0001;		// Region does not track entry/exit
	public static final int REGION_CAMERACLIP			= 0x0002;		// Region collides with cameras
	public static final int REGION_RENDER				= 0x0004;		// Region is intended to be rendered

	// PROJECTILE FLAGS
	public static final int PROJ_EXPLODE_ONTIMER		= 0x1;			// if the projectile has an explosion it will explode on timeout too
	public static final int PROJ_EXPLODE_ONTHING		= 0x2;			// if the projectile has an explosion it will explode against things
	public static final int PROJ_EXPLODE_ONWALL			= 0x4;			// if the projectile has an explosion it will explode against walls
	public static final int PROJ_EXPLODE_ONFLOOR		= 0x8;			// if the projectile has an explosion it will explode against floors
	public static final int PROJ_EXPLODE_ONDAMAGE		= 0x10;			// if the projectile has an explosion it will explode if damaged
	public static final int PROJ_INSTANT_FIRE			= 0x20;			// Projectile travels instantly
	public static final int PROJ_SILVER					= 0x40;			// Projectile is silver
	public static final int PROJ_STAKING				= 0x80;			// Projectile has a chance of staking its victim
	public static final int PROJ_CANDODGE				= 0x100;		// Projectile can be dodged
	public static final int PROJ_BULLET					= 0x200;		// Projectile is a bullet-type projectile (special damage-type rules apply)


	// EMITTER TYPES (this is all obsolete -- emitter system was changed)
	public static final int EMITTER_TYPE_NONE			= 0;			// invalid
	public static final int EMITTER_TYPE_GAS			= 1;
	public static final int EMITTER_TYPE_FIRE			= 2;

	// EMITTER MEMBER CONSTANTS
	// int emitter members
	public static final int 	EMITTER_MEMBER_RATE						= 1;
	public static final int 	EMITTER_MEMBER_LIFESPAN					= 2;

	public static final int 	EMITTER_MEMBER_LIQ_NUMSTREAKS			= 60;
	public static final int 	EMITTER_MEMBER_LIQ_MINSTREAK			= 61;
	public static final int 	EMITTER_MEMBER_LIQ_MAXSTREAK			= 62;

	// float emitter members
	public static final int 	EMITTER_MEMBER_SIZE						= 500;
	public static final int 	EMITTER_MEMBER_MINDIST					= 501;
	public static final int 	EMITTER_MEMBER_MAXDIST					= 502;
	public static final int 	EMITTER_MEMBER_INITVEL					= 503;

	public static final int 	EMITTER_MEMBER_GAS_DRAG					= 520;
	public static final int 	EMITTER_MEMBER_GAS_GRAVITY				= 521;
	public static final int 	EMITTER_MEMBER_GAS_TURBULENCE			= 522;
	public static final int 	EMITTER_MEMBER_GAS_GROWTHRATE			= 523;

	public static final int 	EMITTER_MEMBER_FIR_TURBULENCE			= 540;

	public static final int 	EMITTER_MEMBER_LIQ_STARTALPHA			= 560;
	public static final int 	EMITTER_MEMBER_LIQ_ENDALPHA				= 561;
	public static final int 	EMITTER_MEMBER_LIQ_GRAVITY				= 562;
	public static final int 	EMITTER_MEMBER_LIQ_COHESION				= 563;
	public static final int 	EMITTER_MEMBER_LIQ_SIZEVARIANCE			= 564;
	public static final int 	EMITTER_MEMBER_LIQ_VELOCITYVARIANCE		= 565;
	public static final int 	EMITTER_MEMBER_LIQ_STREAKCOHESION		= 566;

	// vector emitter members
	public static final int 	EMITTER_MEMBER_STARTCOLOR				= 1000;
	public static final int 	EMITTER_MEMBER_ENDCOLOR					= 1001;


	
	// light styles
	public static final int LIGHTSTYLE_CONSTANT			=	0;			// Always on constant intensity
	public static final int LIGHTSTYLE_TORCH1			=	1;			// Flickering torch, low turbulence
	public static final int LIGHTSTYLE_TORCH2			=	2;			// Flickering torch, med turbulence
	public static final int LIGHTSTYLE_TORCH3			=	3;			// Flickering torch, high turbulence
	public static final int LIGHTSTYLE_PULSE1			=	4;			// Slow pulse
	public static final int LIGHTSTYLE_PULSE2			=	5;			// Medium pulse, low val .2
	public static final int LIGHTSTYLE_PULSE3			=	6;			// Fast pulse, low val .5
	public static final int LIGHTSTYLE_FLUORO1			=	7;			// Slow flourescent flicker
	public static final int LIGHTSTYLE_FLUORO2			=	8;			// Med flourescent flicker
	public static final int LIGHTSTYLE_FLUORO3			=	9;			// Fast flourescent flicker

	// MOTIONSET MODES
	public static final int MOTION_INVALID				= 0;
	public static final int MOTION_WALK					= 1;
	public static final int MOTION_WALK_ONEHAND			= 2;
	public static final int MOTION_WALK_AXE2			= 3;
	public static final int MOTION_WALK_SWORD2			= 4;
	public static final int MOTION_WALK_POLEARM			= 5;
	public static final int MOTION_WALK_GUN				= 6;
	public static final int MOTION_WALK_GUN2			= 7;
	public static final int MOTION_WALK_BOW				= 8;
	public static final int MOTION_WALK_SHOTGUN			= 9;
	public static final int MOTION_WALK_CROSSBOW		= 10;
	public static final int MOTION_WALK_ROCKET			= 11;
	public static final int MOTION_WALK_CHAINSAW		= 12;
	public static final int MOTION_WALK_CHAINGUN		= 13;
	public static final int MOTION_WALK_UTIL			= 14;
	public static final int MOTION_WALK_UTIL_ONEHAND	= 15;
	public static final int MOTION_WALK_UTIL_GUN		= 16;
	public static final int MOTION_RUN					= 17;
	public static final int MOTION_RUN_ONEHAND			= 18;
	public static final int MOTION_RUN_AXE2				= 19;
	public static final int MOTION_RUN_SWORD2			= 20;
	public static final int MOTION_RUN_POLEARM			= 21;
	public static final int MOTION_RUN_GUN				= 22;
	public static final int MOTION_RUN_GUN2				= 23;
	public static final int MOTION_RUN_BOW				= 24;
	public static final int MOTION_RUN_SHOTGUN			= 25;
	public static final int MOTION_RUN_CROSSBOW			= 26;
	public static final int MOTION_RUN_ROCKET			= 27;
	public static final int MOTION_RUN_CHAINSAW			= 28;
	public static final int MOTION_RUN_CHAINGUN			= 29;
	public static final int MOTION_RUN_UTIL				= 30;
	public static final int MOTION_RUN_UTIL_ONEHAND		= 31;
	public static final int MOTION_RUN_UTIL_GUN			= 32;
	public static final int MOTION_STAND				= 33;
	public static final int MOTION_STAND_ONEHAND		= 34;
	public static final int MOTION_STAND_AXE2			= 35;
	public static final int MOTION_STAND_SWORD2			= 36;
	public static final int MOTION_STAND_POLEARM		= 37;
	public static final int MOTION_STAND_GUN			= 38;
	public static final int MOTION_STAND_GUN2			= 39;
	public static final int MOTION_STAND_BOW			= 40;
	public static final int MOTION_STAND_SHOTGUN		= 41;
	public static final int MOTION_STAND_CROSSBOW		= 42;
	public static final int MOTION_STAND_ROCKET			= 43;
	public static final int MOTION_STAND_CHAINSAW		= 44;
	public static final int MOTION_STAND_CHAINGUN		= 45;
	public static final int MOTION_STAND_UTIL			= 46;
	public static final int MOTION_STAND_UTIL_ONEHAND	= 47;
	public static final int MOTION_STAND_UTIL_GUN		= 48;
	public static final int MOTION_IDLE					= 49;
	public static final int MOTION_IDLE_ONEHAND			= 50;
	public static final int MOTION_IDLE_AXE2			= 51;
	public static final int MOTION_IDLE_SWORD2			= 52;
	public static final int MOTION_IDLE_POLEARM			= 53;
	public static final int MOTION_IDLE_GUN				= 54;
	public static final int MOTION_IDLE_GUN2			= 55;
	public static final int MOTION_IDLE_BOW				= 56;
	public static final int MOTION_IDLE_SHOTGUN			= 57;
	public static final int MOTION_IDLE_CROSSBOW		= 58;
	public static final int MOTION_IDLE_ROCKET			= 59;
	public static final int MOTION_IDLE_CHAINSAW		= 60;
	public static final int MOTION_IDLE_CHAINGUN		= 61;
	public static final int MOTION_IDLE_UTIL			= 62;
	public static final int MOTION_IDLE_UTIL_ONEHAND	= 63;
	public static final int MOTION_IDLE_UTIL_GUN		= 64;
	 // Attacks
	public static final int MOTION_CLAW					= 65;
	public static final int MOTION_SWORDSLASH			= 66;
	public static final int MOTION_SWORDSLASH2			= 67;
	public static final int MOTION_SWORDTHRUST			= 68;
	public static final int MOTION_SWORDTHRUST2			= 69;
	public static final int MOTION_BLUNTBASH			= 70;
	public static final int MOTION_BLUNTBASH2			= 71;
	public static final int MOTION_BOW					= 72;
	public static final int MOTION_CROSSBOW				= 73;
	public static final int MOTION_DAGGERSTAB			= 74;
	public static final int MOTION_DAGGERCUT			= 75;
	public static final int MOTION_SWING				= 76;
	public static final int MOTION_PISTOL				= 77;
	public static final int MOTION_RIFLEAIMED			= 78;
	public static final int MOTION_SHOTGUN				= 79;
	public static final int MOTION_MACHINEGUN			= 80;
	public static final int MOTION_ROCKET				= 81;
	public static final int MOTION_STAKE				= 82;
	public static final int MOTION_THROW				= 83;
	public static final int MOTION_POLETHRUST			= 84;
	public static final int MOTION_POLESLASH			= 85;
	public static final int MOTION_BLUNTSLASH			= 86;
	public static final int MOTION_BLUNTSLASH2			= 87;
	public static final int MOTION_CLAWSPECIAL			= 88;
	public static final int MOTION_PUNCH				= 89;
	public static final int MOTION_CHAINSAW				= 90;
	public static final int MOTION_TOSS					= 91;
	public static final int MOTION_UZI					= 92;
	public static final int MOTION_FLAMETHROWER			= 93;
	public static final int MOTION_CHAINGUN				= 94;
	public static final int MOTION_UTIL_SWORDSLASH		= 95;
	public static final int MOTION_UTIL_SWORDTHRUST		= 96;
	public static final int MOTION_UTIL_BLUNTBASH		= 97;
	public static final int MOTION_UTIL_DAGGERSTAB		= 98;
	public static final int MOTION_UTIL_DAGGERCUT		= 99;
	public static final int MOTION_UTIL_SWING			= 100;
	public static final int MOTION_UTIL_PISTOL			= 101;
	public static final int MOTION_UTIL_STAKE			= 102;
	public static final int MOTION_UTIL_THROW			= 103;
	public static final int MOTION_UTIL_BLUNTSLASH		= 104;
	public static final int MOTION_UTIL_PUNCH			= 105;
	public static final int MOTION_UTIL_TOSS			= 106;
	public static final int MOTION_UTIL_UZI				= 107;
	 // Blocks & Defenses
	public static final int MOTION_BLOCK1H				= 108;
	public static final int MOTION_BLOCK2H				= 109;
	public static final int MOTION_PARRY1H				= 110;
	public static final int MOTION_PARRY2H				= 111;
	public static final int MOTION_SHIELD				= 112;
	public static final int MOTION_DUCK					= 113;
	 // Reactions
	public static final int MOTION_DAMAGELIGHT			= 114;
	public static final int MOTION_DAMAGEMEDIUM			= 115;
	public static final int MOTION_DAMAGEHEAVY			= 116;
	public static final int MOTION_TALK					= 117;
	public static final int MOTION_LISTEN				= 118;
	public static final int MOTION_FEED					= 119;
	public static final int MOTION_EMBRACED				= 120;
	public static final int MOTION_DEATHQUICK			= 121;
	public static final int MOTION_DEATHSLOW			= 122;
	public static final int MOTION_DEATHDRAMATIC		= 123;
	public static final int MOTION_DEATHSUNLIGHT		= 124;
	public static final int MOTION_DEATHSTAKED			= 125;
	public static final int MOTION_DEATHCLAWED			= 126;
	public static final int MOTION_CREATE				= 127;
	public static final int MOTION_FEEDING				= 128;
	public static final int MOTION_DRAINING				= 129;
	public static final int MOTION_FEEDRELEASE			= 130;
	public static final int MOTION_FEEDERDRAINED		= 131;
	public static final int MOTION_DEATHDRAINED			= 132;
	public static final int MOTION_FEEDRELEASED			= 133;
	public static final int MOTION_FEEDWRIST			= 134;
	public static final int MOTION_EMBRACEDWRIST		= 135;
	public static final int MOTION_FEEDINGWRIST			= 136;
	public static final int MOTION_DRAININGWRIST		= 137;
	public static final int MOTION_FEEDRELEASEWRIST		= 138;
	public static final int MOTION_FEEDRELEASEDWRIST	= 139;
	 // specials
	public static final int MOTION_SPECIAL1				= 140;
	public static final int MOTION_SPECIAL2				= 141;
	public static final int MOTION_SPECIAL3				= 142;
	public static final int MOTION_SPECIAL4				= 143;
	public static final int MOTION_SPECIAL5				= 144;
	public static final int MOTION_SPELL				= 145;
	public static final int MOTION_SPELLHANDS			= 146;
	public static final int MOTION_SPELLTHROW			= 147;
	public static final int MOTION_SPELLSPIT			= 148;
	public static final int MOTION_AWAKEN				= 149;
	public static final int MOTION_UNSTAKE				= 150;
	public static final int MOTION_UNSTAKED				= 151;
	public static final int MOTION_GESTURE1				= 152;
	public static final int MOTION_GESTURE2				= 153;
	public static final int MOTION_GESTURE3				= 154;
	public static final int MOTION_GESTURE4				= 155;
	public static final int MOTION_GESTURE5				= 156;

	public static final int MOTION_ACTION1				= 157;
	public static final int MOTION_ACTION2				= 158;
	public static final int MOTION_ACTION3				= 159;
	public static final int MOTION_ACTION4				= 160;
	public static final int MOTION_ACTION5				= 161;
	public static final int MOTION_ACTION6				= 162;
	public static final int MOTION_ACTION7				= 163;
	public static final int MOTION_ACTION8				= 164;
	public static final int MOTION_ACTION9				= 165;
	public static final int MOTION_ACTION10				= 166;

	public static final int MOTION_CONFUSED				= 167;
	public static final int MOTION_CROUCH				= 168;

	public static final int MOTION_DODGE				= 169;
	public static final int MOTION_DODGE_ONEHAND		= 170;
	public static final int MOTION_DODGE_AXE2			= 171;
	public static final int MOTION_DODGE_SWORD2			= 172;
	public static final int MOTION_DODGE_POLEARM		= 173;
	public static final int MOTION_DODGE_GUN			= 174;
	public static final int MOTION_DODGE_GUN2			= 175;
	public static final int MOTION_DODGE_BOW			= 176;
	public static final int MOTION_DODGE_SHOTGUN		= 177;
	public static final int MOTION_DODGE_CROSSBOW		= 178;
	public static final int MOTION_DODGE_ROCKET			= 179;
	public static final int MOTION_DODGE_CHAINSAW		= 180;
	public static final int MOTION_DODGE_CHAINGUN		= 181;
	public static final int MOTION_DODGE_UTIL			= 182;
	public static final int MOTION_DODGE_UTIL_ONEHAND	= 183;
	public static final int MOTION_DODGE_UTIL_GUN		= 184;

	public static final int MOTION_SPECIAL6				= 185;
	public static final int MOTION_SPECIAL7				= 186;
	public static final int MOTION_SPECIAL8				= 187;
	public static final int MOTION_SPECIAL9				= 188;
	public static final int MOTION_SPECIAL10			= 189;

	public static final int MOTION_GESTURE6				= 190;
	public static final int MOTION_GESTURE7				= 191;
	public static final int MOTION_GESTURE8				= 192;
	public static final int MOTION_GESTURE9				= 193;
	public static final int MOTION_GESTURE10			= 194;

	public static final int MOTION_LIMP					= 195;
	public static final int MOTION_LIMP_ONEHAND			= 196;
	public static final int MOTION_LIMP_AXE2			= 197;
	public static final int MOTION_LIMP_SWORD2			= 198;
	public static final int MOTION_LIMP_POLEARM			= 199;
	public static final int MOTION_LIMP_GUN				= 200;
	public static final int MOTION_LIMP_GUN2			= 201;
	public static final int MOTION_LIMP_BOW				= 202;
	public static final int MOTION_LIMP_SHOTGUN			= 203;
	public static final int MOTION_LIMP_CROSSBOW		= 204;
	public static final int MOTION_LIMP_ROCKET			= 205;
	public static final int MOTION_LIMP_CHAINSAW		= 206;
	public static final int MOTION_LIMP_CHAINGUN		= 207;
	public static final int MOTION_LIMP_UTIL			= 208;
	public static final int MOTION_LIMP_UTIL_ONEHAND	= 209;
	public static final int MOTION_LIMP_UTIL_GUN		= 210;

	public static final int MOTION_SPECIAL11			= 211;
	public static final int MOTION_SPECIAL12			= 212;
	public static final int MOTION_SPECIAL13			= 213;
	public static final int MOTION_SPECIAL14			= 214;
	public static final int MOTION_SPECIAL15			= 215;
	public static final int MOTION_SPECIAL16			= 216;
	public static final int MOTION_SPECIAL17			= 217;
	public static final int MOTION_SPECIAL18			= 218;
	public static final int MOTION_SPECIAL19			= 219;
	public static final int MOTION_SPECIAL20			= 220;

	public static final int MOTION_HURT					= 221;
	public static final int MOTION_HURT_ONEHAND			= 222;
	public static final int MOTION_HURT_AXE2			= 223;
	public static final int MOTION_HURT_SWORD2			= 224;
	public static final int MOTION_HURT_POLEARM			= 225;
	public static final int MOTION_HURT_GUN				= 226;
	public static final int MOTION_HURT_GUN2			= 227;
	public static final int MOTION_HURT_BOW				= 228;
	public static final int MOTION_HURT_SHOTGUN			= 229;
	public static final int MOTION_HURT_CROSSBOW		= 230;
	public static final int MOTION_HURT_ROCKET			= 231;
	public static final int MOTION_HURT_CHAINSAW		= 232;
	public static final int MOTION_HURT_CHAINGUN		= 233;

	public static final int MOTION_FRENZY				= 234;
	public static final int MOTION_HUMANITYLOST			= 235;
	public static final int MOTION_FEEDFAILED			= 236;
 
	public static final int MOTION_THROW1				= 237;
	public static final int MOTION_THROW2				= 238;
	public static final int MOTION_THROW3				= 239;
	public static final int MOTION_THROW4				= 240;
	public static final int MOTION_THROW5				= 241;
 
	public static final int MOTION_THROWN1				= 242;
	public static final int MOTION_THROWN2				= 243;
	public static final int MOTION_THROWN3				= 244;
	public static final int MOTION_THROWN4				= 245;
	public static final int MOTION_THROWN5				= 246;

	public static final int MOTION_THROWMISS			= 247;
	public static final int MOTION_STANDUP				= 248;

	public static final int MOTION_TURNLEFT				= 249;
	public static final int MOTION_TURNRIGHT			= 250;

	public static final int MOTION_SPECIAL21			= 251;
	public static final int MOTION_SPECIAL22			= 252;
	public static final int MOTION_SPECIAL23			= 253;
	public static final int MOTION_SPECIAL24			= 254;
	public static final int MOTION_SPECIAL25			= 255;

	public static final int MOTION_STAKED				= 256;

	public static final int NUM_MOTIONS					= 257;


	// MOTIONSET TAGS
	public static final int MOTIONTAG_INVALID			= 0;

	public static final int MOTIONTAG_ROOT				= 1;
	public static final int MOTIONTAG_LHIP				= 2;
	public static final int MOTIONTAG_LKNEE				= 3;
	public static final int MOTIONTAG_LANKLE			= 4;
	public static final int MOTIONTAG_LFOOT				= 5;
	public static final int MOTIONTAG_RHIP				= 6;
	public static final int MOTIONTAG_RKNEE				= 7;
	public static final int MOTIONTAG_RANKLE			= 8;
	public static final int MOTIONTAG_RFOOT				= 9;
	public static final int MOTIONTAG_BTUNIC1			= 10;
	public static final int MOTIONTAG_BTUNIC2			= 11;
	public static final int MOTIONTAG_BTUNIC3			= 12;
	public static final int MOTIONTAG_FTUNIC1			= 13;
	public static final int MOTIONTAG_FTUNIC2			= 14;
	public static final int MOTIONTAG_BACK1				= 15;
	public static final int MOTIONTAG_BACK2				= 16;
	public static final int MOTIONTAG_CHEST				= 17;
	public static final int MOTIONTAG_NECK				= 18;
	public static final int MOTIONTAG_HEAD				= 19;
	public static final int MOTIONTAG_HELMET			= 20;
	public static final int MOTIONTAG_JAW				= 21;
	public static final int MOTIONTAG_RSHOULDER			= 22;
	public static final int MOTIONTAG_RELBOW			= 23;
	public static final int MOTIONTAG_RWRIST			= 24;
	public static final int MOTIONTAG_RFINGERS			= 25;
	public static final int MOTIONTAG_WEAPON			= 26;
	public static final int MOTIONTAG_LSHOULDER			= 27;
	public static final int MOTIONTAG_LELBOW			= 28;
	public static final int MOTIONTAG_LWRIST			= 29;
	public static final int MOTIONTAG_LFINGERS			= 30;
	public static final int MOTIONTAG_SHIELD			= 31;
	public static final int MOTIONTAG_MCAPE1			= 32;
	public static final int MOTIONTAG_SCABBARD			= 33;

	public static final int MOTIONTAG_USER0				= 34;
	public static final int MOTIONTAG_USER1				= 35;
	public static final int MOTIONTAG_USER2				= 36;
	public static final int MOTIONTAG_USER3				= 37;
	public static final int MOTIONTAG_USER4				= 38;
	public static final int MOTIONTAG_USER5				= 39;
	public static final int MOTIONTAG_USER6				= 40;
	public static final int MOTIONTAG_USER7				= 41;
	public static final int MOTIONTAG_USER8				= 42;
	public static final int MOTIONTAG_USER9				= 43;

	public static final int NUM_MOTIONTAGS				= 44;


	public static final int AIMAIN_FLAG_PAUSED				= 0x1;				// main on/off switch
	public static final int AIMAIN_FLAG_TRACE				= 0x2;				// main trace switch

	public static final int AIMAIN_FLAG_STOPPEDS			= 0x2000;			// stop the peds
	public static final int AIMAIN_FLAG_STOPFLYPEDS			= 0x4000;			// stop the fly peds
	public static final int AIMAIN_FLAG_NOENEMYSEARCH		= 0x8000;			// enemy won't search
	public static final int AIMAIN_FLAG_NOGUARDSEARCH		= 0x10000;			// guard won't search
	public static final int AIMAIN_FLAG_NOPARTYSEARCH		= 0x20000;			// party members won't search
	public static final int AIMAIN_FLAG_NOENEMYCOMBAT		= 0x40000;			// enemies won't attack
	public static final int AIMAIN_FLAG_NOPARTYCOMBAT		= 0x80000;			// party members won't attack
	public static final int AIMAIN_FLAG_NOENEMYCAST			= 0x100000;			// enemies won't cast
	public static final int AIMAIN_FLAG_NOPARTYCAST			= 0x200000;			// party members won't cast
	public static final int AIMAIN_FLAG_NOENEMYCASTHEAL		= 0x400000;			// enemy will not use healing diciplines
	public static final int AIMAIN_FLAG_NOPARTYCASTHEAL		= 0x800000;			// party members will not use healing disciplines
	public static final int AIMAIN_FLAG_NOENEMYCASTBUFF		= 0x1000000;		// enemy will not use buff disciplines
	public static final int AIMAIN_FLAG_NOPARTYCASTBUFF		= 0x2000000;		// party members will not use buff disciplines
	public static final int AIMAIN_FLAG_NOENEMYCASTDAMAGE 	= 0x4000000;		// enemy will not use damage disciplines
	public static final int AIMAIN_FLAG_NOPARTYCASTDAMAGE	= 0x8000000;		// party members will not use damage disciplines
	public static final int AIMAIN_FLAG_NOENEMYCASTSPECIAL	= 0x10000000;		// enemy will not use special disciplines
	public static final int AIMAIN_FLAG_NOPARTYCASTSPECIAL	= 0x20000000;		// party members will not use special disciplines


	public static final int AI_MODE_NONE				= 0;					// AI is in no state (creation only)
	public static final int AI_MODE_PARTY				= 1;					// AI is a friendly party member
	public static final int AI_MODE_PET					= 2;					// AI is a pet of another AI
	public static final int AI_MODE_ENEMY				= 3;					// AI is an active enemy
	public static final int AI_MODE_ENEMYSEARCH			= 4;					// AI is an enemy searching for a target
	public static final int AI_MODE_ASLEEP				= 5;					// AI is an inactive enemy
	public static final int AI_MODE_NEUTRAL				= 6;					// AI is neutral (civilian)
	public static final int AI_MODE_PEDWANDER			= 7;					// AI is a wandering pedestrian
	public static final int AI_MODE_PEDFLY				= 8;					// AI is a flying "pedestrian"
	public static final int AI_MODE_FRENZIED			= 9;					// AI is frenzied
	public static final int AI_MODE_MESMERIZED			= 10;					// AI is mesmerized
	public static final int AI_MODE_DAZED				= 11;					// AI is dazed
	public static final int AI_MODE_AFRAID				= 12;					// AI is afraid
	public static final int AI_MODE_POSSESSING			= 13;					// AI is posesssing something, and should be motionless and just check for damage

	public static final int AI_MAXPETS					= 4;					// do NOT change it to make it higher, it won't help... constant is just defined for disciplines...

	public static final int AIMIND_FLAG_NOAFRAID		= 0x1;
	public static final int AIMIND_FLAG_NODAZED			= 0x2;
	public static final int AIMIND_FLAG_NOMESMERIZED	= 0x4;
	public static final int AIMIND_FLAG_NOPOSSESSED		= 0x8;
	public static final int AIMIND_FLAG_NOMAJESTY		= 0x10;


	// shell effects flags
	public static final int SHELL_FLAG_SHELLONLY		= 0x80;					// Render only the shell texture
	public static final int SHELL_FLAG_HIPRISHELL		= 0x1000;				// Current shell is HIGH priority
	public static final int SHELL_FLAG_MEDPRISHELL		= 0x2000;				// Current shell is MED priority (no flag = LOW priority)
	public static final int SHELL_FLAG_BOUNCESHELL		= 0x4000;				// Bounce current shell alpha
	public static final int SHELL_FLAG_LOOPSHELL		= 0x8000;				// Loop shell alpha level


	// sound stuff
	public static final int SOUND_FLAG_LOOP				= 0x00008;				// loop indefinately
	public static final int SOUND_FLAG_STREAM			= 0x00400;				// a Codex verb was requesting this sound to stream 

	// chronicle info flags
	public static final int CHRON_FLAG_MODERNDAY		= 0x1;					// This chronicle is modern day (UI changes, etc.)
	public static final int CHRON_FLAG_MULTIPLAYER		= 0x2;					// This chronicle is meant for multiplayer
	public static final int CHRON_FLAG_STREQUIRED		= 0x4;					// This chronicle is meant to be played with a ST
	public static final int CHRON_FLAG_HIDE				= 0x8;					// This chronicle is only meant to be chained from another and should not appear in selection UIs
	
	public static final int CHRON_FLAG_USER1			= 0x10000;				// User flag


	// location flags
	public static final int LOCATION_FLAG_NOSAVE		= 0x0001;				// Objects are not saved in this location
	public static final int LOCATION_FLAG_TRANSIENT		= 0x0002;				// Location is normally loaded briefly, so don't free origin world
	public static final int LOCATION_FLAG_DONOTFREE		= 0x0004;				// Location should not be freed!
	public static final int LOCATION_FLAG_SHOWINMAP		= 0x0008;				// Location should be shown on the map
	public static final int LOCATION_FLAG_HAVEN			= 0x0010;				// Location is a haven and should be indicated as such on the map
	public static final int LOCATION_FLAG_NOTELEPORTS	= 0x0020;				// Teleports cannot be created in this location
	public static final int LOCATION_FLAG_CLEARBUFFER	= 0x0040;				// Clearing the buffer is required

	// (internal) These flags can only be set at runtime by the engine and cannot be defined in the NSC file description!
	public static final int LOCATION_FLAG_LOADED		= 0x100000;				// Current pWorld pointer is valid (or should be!)
	public static final int LOCATION_FLAG_SCENECHANGING	= 0x200000;				// Temporary flag to mark that the scene is changing on this location
	public static final int LOCATION_FLAG_ABYSS			= 0x400000;				// Location is an abyss teleportation site 



	// ------------------------------------------------------------------------
	// CODEXQUEST CONSTS
	// ------------------------------------------------------------------------

	// Quick notes: 
	// 1) a guid of 0 for quests is valid (I didn't want to lose one entry in my array)
	//    so the "bad guid" for quests is actually -1
	// 2) MAX_QUEST is the maximum number of simultaneously active quests

	public static final int INVALID_QUEST_GUID			= -1;
	public static final int MAX_QUESTS					= 64;

	public static final int QUEST_FLAG_COMPLETE			= 0x1;

	public static final int QUESTITEM_FLAG_COMPLETE 	= 0x1;
	public static final int QUESTITEM_FLAG_HIDDEN		= 0x2;


	// ------------------------------------------------------------------------
	// CODEXCAMERA CONSTS
	// ------------------------------------------------------------------------

	public static final int CAM_SHOT_NONE				= 0;
	public static final int CAM_SHOT_ANGULAR_A			= 1;
	public static final int CAM_SHOT_ANGULAR_B			= 2;
	public static final int CAM_SHOT_ANGULAR_C			= 3;
	public static final int CAM_SHOT_OTS_A				= 4;
	public static final int CAM_SHOT_OTS_C				= 5;
	public static final int CAM_SHOT_POV_A				= 6;
	public static final int CAM_SHOT_POV_C				= 7;
	public static final int CAM_SHOT_PROFILE_A			= 8;
	public static final int CAM_SHOT_PROFILE_C			= 9;
	public static final int NUM_CAM_SHOTS				= 10;

	public static final int CAM_ANGLE_LOW				= 0;
	public static final int CAM_ANGLE_MEDIUMLOW			= 1;
	public static final int CAM_ANGLE_MEDIUM			= 2;
	public static final int CAM_ANGLE_MEDIUMHIGH		= 3;
	public static final int CAM_ANGLE_HIGH				= 4;
	public static final int NUM_CAM_ANGLES				= 5;

	public static final int CAM_DIST_EXTREMECLOSEUP		= 0;
	public static final int CAM_DIST_CLOSEUP			= 1;
	public static final int CAM_DIST_MEDIUM				= 2;
	public static final int CAM_DIST_FULL				= 3;
	public static final int CAM_DIST_LONG				= 4;
	public static final int NUM_CAM_DISTS				= 5;


	// FIXME!! old, obsolete constants, delete when all scripts changed to the new system
	public static final int CAMERA_SHOT_ECU				= 0;
	public static final int CAMERA_SHOT_MCU				= 1;
	public static final int CAMERA_SHOT_CU				= 2;
	public static final int CAMERA_SHOT_MS				= 3;
	public static final int CAMERA_SHOT_WS				= 4;
	public static final int CAMERA_SHOT_LS				= 5;
	public static final int CAMERA_SHOT_ELS				= 6;
	public static final int CAMERA_SHOT_BG				= 7;
	public static final int CAMERA_SHOT_FG				= 8;
	public static final int CAMERA_SHOT_OTS				= 9;
	public static final int CAMERA_SHOT_POV				= 10;
	public static final int CAMERA_ANGLE_LOW			= 0;
	public static final int CAMERA_ANGLE_MEDIUM			= 1;
	public static final int CAMERA_ANGLE_HIGH			= 2;
	// FIXME!! old, obsolete constants, delete when all scripts changed to the new system

	public static final int CONV_XFLAG_NOAUTOBEGIN		= 0x1;		// conversation will NOT set the conversation UI up
	public static final int CONV_XFLAG_NOAUTOEND		= 0x2;		// conversation will NOT take the conversation UI down
	public static final int CONV_XFLAG_WANTFEEDBACK		= 0x4;		// conversation wants precise feedback on what is happening
	public static final int CONV_XFLAG_ALLOWABORT		= 0x8;		// conversation can be aborted with Esc
	public static final int CONV_XFLAG_NOSKIP			= 0x10;		// conversation lines cannot be skipped
	public static final int CONV_XFLAG_NOTALKLISTEN		= 0x20;		// conversation will not do the automated TALK/LISTEN animations
	public static final int CONV_XFLAG_STOPACTORS		= 0x100;	// Stop actors at start of conversation
	public static final int CONV_XFLAG_NOREMOVEWEAPONS	= 0x200;	// Don't remove actor weapons at start of conversation
	public static final int CONV_XFLAG_NORESTOREWEAPONS = 0x400;	// Don't restore actor weapons at end of conversation

	
	public static final int CONV_BEFLAG_UI				= 0x1;		// bring up/pull down the conversation UI (non automatic begin/end conversations)
	public static final int CONV_BEFLAG_STOPACTORS		= 0x2;		// all the involved actors should be stopped (non automatic begin/end conversations)
	public static final int CONV_BEFLAG_REMOVEWEAPONS	= 0x4;		// that the weapons should be removed from the actors (non automatic begin/end conversations)


	public static final int BUYSELL_XFLAG_NOAUTOBEGIN	= 0x1;		// buy sell will NOT set the conversation UI up
	public static final int BUYSELL_XFLAG_NOAUTOEND		= 0x2;		// buy sell will NOT take the conversation UI down
	public static final int BUYSELL_XFLAG_WANTFEEDBACK	= 0x4;		// buy sell wants precise feedback on what is happening

	public static final int BUYSELL_BEFLAG_UI			= 0x1;		// bring up/pull down the buy/sell UI (non automatic begin/end buy/sells)

	public static final int BUYSELL_ITEM_MUNDANE		= 0x1;		// the shop accepts that you sell it mundane items
	public static final int BUYSELL_ITEM_TREASURE		= 0x2;		// etc.
	public static final int BUYSELL_ITEM_BLOODITEM		= 0x4;
	public static final int BUYSELL_ITEM_POTION			= 0x8;
	public static final int BUYSELL_ITEM_ARMOR			= 0x10;
	public static final int BUYSELL_ITEM_MAGICARMOR		= 0x20;
	public static final int BUYSELL_ITEM_WEAPON			= 0x40;
	public static final int BUYSELL_ITEM_MAGICWEAPON	= 0x80;
	public static final int BUYSELL_ITEM_SCROLLBOOK		= 0x100;

	public static final int GAMEMESSAGE_OUTPUT_CONSOLE  = 0;		// game Message will be output to the console

	public static final int GAMEMESSAGE_SYSID_RESTTOADVANCE  = 1;	// predefined system message

	// actorfeed status
	public static final int ACTORFEED_STATUS_START		= 0;
	public static final int ACTORFEED_STATUS_INPROGRESS	= 1;
	public static final int ACTORFEED_STATUS_STOPPED	= 2;
	public static final int ACTORFEED_STATUS_STOPKILLED	= 3;

	public static final int CONSOLEPRINT_FLAG_SERVERCONSOLE	= 0x1;	// whatever the guid passed, only prints to the server console
	public static final int CONSOLEPRINT_FLAG_BROADCAST		= 0x2;	// whatever the guid passed, send to every client
	public static final int CONSOLEPRINT_FLAG_RADIUSSMALL	= 0x4;	// a small	radius around the guid passed
	public static final int CONSOLEPRINT_FLAG_RADIUSMED		= 0x8;	//	  medium	
	public static final int CONSOLEPRINT_FLAG_RADIUSLARGE	= 0x10;	//	  large

	public static final int ACTOR_SHAPESHIFT_NOWEAPON	= 0x01;		// Don't change weapon on shapeshift
	public static final int ACTOR_SHAPESHIFT_NOHEAD		= 0x02;		// Don't change head on shapeshift
	public static final int ACTOR_SHAPESHIFT_NOFOLEY	= 0x04;		// Don't change foley on shapeshift
	public static final int ACTOR_SHAPESHIFT_WEAPONLESS	= 0x08;		// Switch to NULL weapon and don't let the player switch to anything else!

	// effect origin values
	public static final int	EI_FLAG_WORN				= 1;		// this effect comes from an item being worn (creatorGuid is the item)
	public static final int	EI_FLAG_USE					= 2;		// this effect comes from an item being used (creatorGuid is the item, good chance of being destroyed tho)
	public static final int	EI_FLAG_DISCIPLINE			= 4;		// this effect comes from a discipline being cast (creator guid is the caster)
	

/**
 * Returns a copyright line
 *
 * <!-- 01/13/99 [YB] Original Programmer -->
*/
	public static final String _NSICopyright = "Copyright (c) Nihilistic Software, Inc. 1998-2000";


/**
 * Gets a chronicle script "reference".
 *
 * @param       index the index in the chronicle scripts
 * @return      the reference of the chronicle script, or 0 in case of failure
 * <!-- 03/03/99 [YB] Original Programmer -->
*/
	public native Object GetChronicleScript(int index);


/**
 * Spawns a class instance from a class name.
 *
 * @param       scriptName the name of the class to spawn an instance from (a .class file, without the extension in the name)
 * @return      the guid of the spawned script, or 0 in case of failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public static native int Spawn(String scriptName);

/**
 * Gets a local string from the string table. (Be careful, there are other localization issues than just translating the string!)
 *
 * @param       stringId the id of the string in the string table
 * @return      a Codex string with the corresponding localized string
 * <!-- 02/08/99 [YB] Original Programmer -->
*/
	public static native String GetLocalizedString(String stringId);


/**
 * Sets a material to a specific frame
 *
 * @param       material the name of the material to act on
 * @param       frame the frame number
 * <!-- 11/12/99 [YB] Original Programmer -->
*/
	public static native void MaterialSetFrame(String material, int frame);


/**
 * Sets a material to page flip
 *
 * @param       material the name of the material to act on
 * @param       msPerFrame the number of milliseconds a frame should last
 * <!-- 11/12/99 [YB] Original Programmer -->
*/
	public static native void MaterialPageFlip(String material, int msPerFrame);


/**
 * Sets a material to flicker
 *
 * @param       material the name of the material to act on
 * @param       lightStyle a flicker light style (a LIGHTSTYLE_ constant)
 * <!-- 11/12/99 [YB] Original Programmer -->
*/
	public static native void MaterialFlicker(String material, int lightStyle);


/**
 * Sets a material to glow ramp
 *
 * @param       material the name of the material to act on
 * @param       lightStyle a glow ramp light style (a LIGHTSTYLE_ constant)
 * <!-- 11/12/99 [YB] Original Programmer -->
*/
	public static native void MaterialGlowRamp(String material, int lightStyle);

/**
 * Sets a material texture to slide
 *
 * @param       material the name of the material to act on
 * @param       u the u slide
 * @param       v the v slide
 * @param       texNum the texture number in the material
 * <!-- 11/12/99 [YB] Original Programmer -->
*/
	public static native void MaterialSlide(String material, float u, float v, int texNum);

/**
 * Sets a material texture to playback from a video stream
 *
 * @param       material the name of the material to act on
 * @param       binkName the name of the bink file to stream from
 * @param       bPreload should the bink file be preloaded into memory
 * @param       bLoop should the file loop
 * @param       bHWUpdate FIXME!! don't remember, sorry...
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public static native void MaterialPlayback(String material, String binkName, boolean bPreload, boolean bLoop, boolean bHWUpdate);

/**
 * Puts a model into the model cache (this MIGHT help loading times later on IF the cache doesn't become full)
 *
 * @param       modelName the name of the model to precache
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public static native boolean PreloadModel(String modelName);

/**
 * Puts a template into the caches (model, sounds, etc.) (this MIGHT help loading times later on IF the caches don't become full)
 *
 * @param       templateName the name of the template to precache
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public static native boolean PreloadTemplate(String templateName);

/**
 * Enables a lightstyle
 *
 * @param       lightStyle the number of the light style
 * <!-- 12/06/99 [YB] Original Programmer -->
*/
	public static native void EnableLightStyle(int lightStyle);

/**
 * Disables a lightstyle
 *
 * @param       lightStyle the number of the light style
 * <!-- 12/06/99 [YB] Original Programmer -->
*/
	public static native void DisableLightStyle(int lightStyle);

/**
 * Adds a game message with the text passed as a string table entry by the user
 *
 * @param       stringId the string table id of the string to output
 * @param       output the output "channel" for the message (a GAMEMESSAGE_OUTPUT_ constant)
 * @param       expire the expiration time of the message in ms (0 for infinite)
 * @param       repeat how many times to repeat the message (0 for infinite)
 * @param       frequency the frequency of the message in ms
 * @return      a message guid, or 0 for failure
 * <!-- 12/07/99 [YB] Original Programmer -->
*/
	public static native int	MessageAddUser(String stringId, int output, int expire, int repeat, int frequency);

/**
 * Adds a game message with the text coming from the string table, with id = GAMEMESSAGE_SYSTEMn where n is the systemId passed
 *
 * @param       systemId the value to concatenate to "GAMEMESSAGE_SYSTEM" to get the string table id.
 * @param       output the output "channel" for the message (a GAMEMESSAGE_OUTPUT_ constant)
 * @param       expire the expiration time of the message in ms (0 for infinite)
 * @param       repeat how many times to repeat the message (0 for infinite)
 * @param       frequency the frequency of the message in ms
 * @return      a message guid, or 0 for failure
 * <!-- 12/07/99 [YB] Original Programmer -->
*/
	public static native int	MessageAddSystem(int systemId, int output, int expire, int repeat, int frequency);

/**
 * Removes a game message by guid.
 *
 * @param       guid the guid of the message to remove
 * <!-- 12/07/99 [YB] Original Programmer -->
*/
	public static native void	MessageRemoveByGuid(int guid);

/**
 * Removes a game message by stringId (this function will remove multiple messages with the same stringId)
 *
 * @param       stringId the stringId of the message to remove
 * <!-- 12/07/99 [YB] Original Programmer -->
*/
	public static native void	MessageRemoveByStringId(String stringId);

/**
 * Removes a game message by system id (this function will remove multiple messages with the same systemId)
 *
 * @param       systemId the systemId of the message to remove
 * <!-- 12/07/99 [YB] Original Programmer -->
*/
	public static native void	MessageRemoveBySystemId(int systemId);

/**
 * Checks to see if the passed guid is a valid actor guid
 *
 * @param       guid a guid
 * @return      success or failure
 * <!-- 03/22/99 [YB] Original Programmer -->
*/
	public static native boolean IsActorGuid(int guid);

/**
 * Checks to see if the passed guid is a valid player guid
 *
 * @param       guid a guid
 * @return      success or failure
 * <!-- 03/22/99 [YB] Original Programmer -->
*/
	public static native boolean IsPlayerGuid(int guid);

/**
 * Checks to see if the passed guid is a valid item guid
 *
 * @param       guid a guid
 * @return      success or failure
 * <!-- 03/22/99 [YB] Original Programmer -->
*/
	public static native boolean IsItemGuid(int guid);

/**
 * Checks to see if the passed guid is a valid armor guid
 *
 * @param       guid a guid
 * @return      success or failure
 * <!-- 03/22/99 [YB] Original Programmer -->
*/
	public static native boolean IsArmorGuid(int guid);

/**
 * Checks to see if the passed guid is a valid weapon guid
 *
 * @param       guid a guid
 * @return      success or failure
 * <!-- 03/22/99 [YB] Original Programmer -->
*/
	public static native boolean IsWeaponGuid(int guid);

/**
 * Checks to see if the passed guid is a valid region guid
 *
 * @param       guid a guid
 * @return      success or failure
 * <!-- 03/22/99 [YB] Original Programmer -->
*/
	public static native boolean IsRegionGuid(int guid);


/**
 * Checks to see if the passed guid is a valid prop guid
 *
 * @param       guid a guid
 * @return      success or failure
 * <!-- 03/22/99 [YB] Original Programmer -->
*/
	public static native boolean IsPropGuid(int guid);

/**
 * Restocks all the currently defined stores
 *
 * <!-- 09/08/99 [YB] Original Programmer -->
*/

	public native void		RefillAllBuySells();

/**
 * Formats a string made out of string table strings (this has issues with international versions, lookup PrintFormatNLS)
 *
 * @return      the formatted string
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public static native String FormatNLS(String aString, String aFormat);

/**
 * Are we connected to a multiplayer game ?
 *
 * @return      true or false
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public static native boolean NetIsConnected();

/**
 * Are we the server in a multiplayer game ?
 *
 * @return      true or false
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public static native boolean NetIsServer();

/**
 * Are we the client in a multiplayer game ?
 *
 * @return      true or false
 * <!-- 11/29/99 [YB] Original Programmer -->
*/
	public static native boolean NetIsClient();

/**
 * Are conversations disabled by the ST ?
 *
 * @return      true or false
 * <!-- 01/03/00 [YB] Original Programmer -->
*/
	public static native boolean NetIsNoAutoConversations();

/**
 * Is automatic advancement disabled by the ST ?
 *
 * @return      true or false
 * <!-- 01/03/00 [YB] Original Programmer -->
*/
	public static native boolean NetIsNoAutoAdvance();

/**
 * Returns the main AI flags
 *
 * @return      the main AI flags, a composition of AIMAIN_FLAG_ constants
 * <!-- 12/03/99 [YB] Original Programmer -->
*/
	public static native int	AIGetMainFlags();

/**
 * Sets some of the main AI flags
 *
 * @param		flags some flags to set (a composition of AIMAIN_FLAG_ constants)
 * @return      the old main AI flags (a composition of AIMAIN_FLAG_ constants)
 * <!-- 12/03/99 [YB] Original Programmer -->
*/
	public static native int	AISetMainFlags(int flags);

/**
 * Clears some of the main AI flags
 *
 * @param		flags some flags to clear (a composition of AIMAIN_FLAG_ constants)
 * @return      the old main AI flags (a composition of AIMAIN_FLAG_ constants)
 * <!-- 12/03/99 [YB] Original Programmer -->
*/
	public static native int	AIClearMainFlags(int flags);

	// Unused AFAIK
	public native void			BeginCutscene(int starterGuid, int flags);
	public native void			EndCutscene(int starterGuid, int flags);


/**
 * Gets the name of the weather effect in the current scene
 *
 * @return      the name of the weather effect
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public static native String	GetWeatherEffect();

/**
 * Sets the weather effect in the current scene
 *
 * @param      weatherEffect the name of the weather effect to use
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public static native void	SetWeatherEffect(String weatherEffect);

/**
 * Clears all floaty text on the specified client
 *
 * @param      clientGuid the client to clear floaty text on
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native void			ClearAllFloatTexts(int clientGuid);

/**
 * Display floaty text on the specified client
 *
 * @param      clientGuid the client to clear floaty text on
 * @param      floatText the float text to display
 * @param      fontSize the font size to use
 * @param      x x coord
 * @param      y y coord
 * @param      expireTime how long the text should remain
 * @param      fadeIn the fade in time
 * @param      fadeOut the fade out time
 * @param      scrollSpeed the scrolling speed
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public native void			DisplayFloatText(int clientGuid, String floatText, int fontSize, int x, int y, int expireTime, int fadeIn, int fadeOut, int scrollSpeed);


	// ------------------------------------------------------------------------

	protected int guid = 0;

	// ------------------------------------------------------------------------
	// These functions require a scriptGuid, use the shell functions below to do that automatically for you
	private native boolean	SetTimer(int scriptGuid, int timerDelay, int timerId, float param0, float param1, float param2, float param3);
	private native boolean	KillTimer(int scriptGuid, int timerId);
	private native boolean	KillAllTimers(int scriptGuid);

	private native boolean	CaptureThing(int scriptGuid, int thingGuid, int captureId);
	private native boolean	ReleaseThing(int scriptGuid, int thingGuid);

	private native int		GetClassThing(int scriptGuid);

	private native boolean	ExecuteConversation(int scriptGuid, String conversationName, String conversationFile, int flags, int starterGuid, int npcGuid);
	private native boolean	BeginConversation(int scriptGuid, int flags, int starterGuid);
	private native boolean	EndConversation(int scriptGuid, int flags, int starterGuid);

	private native boolean	ExecuteBuySell(int scriptGuid, int thingGuid, int shopperGuid, int flags);
	private native boolean	AddBuySell(int scriptGuid, String filename, int thingGuid, String descriptionId, int flags, float buyRate, float sellRate, int sellFlags);
	private native boolean	BeginBuySell(int scriptGuid, int flags);
	private native boolean	EndBuySell(int scriptGuid, int flags);

	private native boolean	ExecuteText(int scriptGuid, int ownerGuid, int thingGuid, String textName);

	private native boolean  PlayVideo(int scriptGuid, String binkName, int id);


	// ------------------------------------------------------------------------
	// NON NATIVES
	// ------------------------------------------------------------------------

/**
 * Gets the script's GUID.
 * 
 * @return      the script's GUID.
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public int GetGUID()
	{
		return(guid);
	}

/**
 * Sets a default timer (ID = 0).
 *
 * @param       timerDelay the delay in seconds before the timer fires
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean SetTimer(float timerDelay)
	{
		return(SetTimer(GetGUID(), (int)(timerDelay * 1000), 0, (float)0.0, (float)0.0, (float)0.0, (float)0.0));
	}

/**
 * Sets a timer with an ID.
 *
 * @param       timerDelay the delay in seconds before the timer fires
 * @param       timerID the identifier for the timer
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean SetTimer(float timerDelay, int timerID)
	{
		return(SetTimer(GetGUID(), (int)(timerDelay * 1000), timerID, (float)0.0, (float)0.0, (float)0.0, (float)0.0));
	}

/**
 * Sets a timer with an ID and 1 user parameter.
 *
 * @param       timerDelay the delay in seconds before the timer fires
 * @param       timerID the identifier for the timer
 * @param       param0 an arbitrary parameter that can be retrieved when the timer fires
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean SetTimer(float timerDelay, int timerID, float param0)
	{
		return(SetTimer(GetGUID(), (int)(timerDelay * 1000), timerID, param0, (float)0.0, (float)0.0, (float)0.0));
	}

/**
 * Sets a timer with an ID and 2 user parameters.
 *
 * @param       timerDelay the delay in seconds before the timer fires
 * @param       timerID the identifier for the timer
 * @param       param0 an arbitrary parameter that can be retrieved when the timer fires
 * @param       param1 an arbitrary parameter that can be retrieved when the timer fires
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean SetTimer(float timerDelay, int timerID, float param0, float param1)
	{
		return(SetTimer(GetGUID(), (int)(timerDelay * 1000), timerID, param0, param1, (float)0.0, (float)0.0));
	}

/**
 * Sets a timer with an ID and 3 user parameters.
 *
 * @param       timerDelay the delay in seconds before the timer fires
 * @param       timerID the identifier for the timer
 * @param       param0 an arbitrary parameter that can be retrieved when the timer fires
 * @param       param1 an arbitrary parameter that can be retrieved when the timer fires
 * @param       param2 an arbitrary parameter that can be retrieved when the timer fires
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean SetTimer(float timerDelay, int timerID, float param0, float param1, float param2)
	{
		return(SetTimer(GetGUID(), (int)(timerDelay * 1000), timerID, param0, param1, param2, (float)0.0));
	}

/**
 * Sets a timer with an ID and 4 user parameters.
 *
 * @param       timerDelay the delay in seconds before the timer fires
 * @param       timerID the identifier for the timer
 * @param       param0 an arbitrary parameter that can be retrieved when the timer fires
 * @param       param1 an arbitrary parameter that can be retrieved when the timer fires
 * @param       param2 an arbitrary parameter that can be retrieved when the timer fires
 * @param       param3 an arbitrary parameter that can be retrieved when the timer fires
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean SetTimer(float timerDelay, int timerID, float param0, float param1, float param2, float param3)
	{
		return(SetTimer(GetGUID(), (int)(timerDelay * 1000), timerID, param0, param1, param2, param3));
	}

/**
 * Kills a default timer (ID = 0).
 *
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean KillTimer()
	{
		return(KillTimer(GetGUID(), 0));
	}

/**
 * Kills a timer with an ID.
 *
 * @param       timerID the identifier for the timer
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean KillTimer(int timerID)
	{
		return(KillTimer(GetGUID(), timerID));
	}

/**
 * Kills all timers.
 *
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean KillAllTimers()
	{
		return(KillAllTimers(GetGUID()));
	}

/**
 * Sets a default capture (ID = 0).
 *
 * @param       thingGuid the thing to capture
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean CaptureThing(int thingGuid)
	{
		return(CaptureThing(GetGUID(), thingGuid, 0));
	}

/**
 * Sets a default capture with an ID.
 *
 * @param       thingGuid the thing to capture
 * @param       captureId the ID to be passed to events
 * @return      success or failure
 * <!-- 01/15/99 [YB] Original Programmer -->
*/
	public boolean CaptureThing(int thingGuid, int captureId)
	{
		return(CaptureThing(GetGUID(), thingGuid, captureId));
	}

/**
 * Releases this script's capture on the passed thing.
 *
 * @param       thingGuid the thing to release
 * @return      success or failure
 * <!-- 01/20/99 [YB] Original Programmer -->
*/
	public boolean ReleaseThing(int thingGuid)
	{
		return(ReleaseThing(GetGUID(), thingGuid));
	}

/**
 * Gets the class thing of this script (i.e. the thing this script is a class (or "template") script of)
 *
 * @return      the guid of the class thing, or 0 for failure
 * <!-- 01/21/99 [YB] Original Programmer -->
*/
	public int GetClassThing()
	{
		return(GetClassThing(GetGUID()));
	}

/**
 * Executes a conversation with default execution flags.
 *
 * @param       conversationName the name of the conversation
 * @param       fileName the name of the conversation file
 * @return      success or failure
 * <!-- 01/20/99 [YB] Original Programmer -->
*/
	public boolean	ExecuteConversation(int starterGuid, int npcGuid, String conversationName, String conversationFile)
	{
		return(ExecuteConversation(GetGUID(), conversationName, conversationFile, 0, starterGuid, npcGuid));
	}

/**
 * Executes a conversation.
 *
 * @param       conversationName the name of the conversation
 * @param       fileName the name of the conversation file
 * @param       flags execution flags (from the CONV_XFLAG_ series)
 * @return      success or failure
 * <!-- 01/20/99 [YB] Original Programmer -->
*/
	public boolean	ExecuteConversation(int starterGuid, int npcGuid, String conversationName, String conversationFile, int flags)
	{
		return(ExecuteConversation(GetGUID(), conversationName, conversationFile, flags, starterGuid, npcGuid));
	}

/**
 * Begins a conversation (use only for conversations with CONV_XFLAG_NOAUTOBEGIN).
 *
 * @param       flags the begin/end flags for the conversation (from the CONV_BEFLAG_ series)
 * @return      success or failure
 * <!-- 02/01/99 [YB] Original Programmer -->
*/
	public boolean	BeginConversation(int starterGuid, int flags)
	{
		return(BeginConversation(GetGUID(), flags, starterGuid));
	}

/**
 * Ends a conversation (use only for conversations with CONV_XFLAG_NOAUTOEND).
 *
 * @param       flags the begin/end flags for the conversation (from the CONV_BEFLAG_ series)
 * @return      success or failure
 * <!-- 02/01/99 [YB] Original Programmer -->
*/
	public boolean	EndConversation(int starterGuid, int flags)
	{
		return(EndConversation(GetGUID(), flags, starterGuid));
	}

/**
 * Executes a buy/sell (shop).
 *
 * @param       thingGuid the giud of the "shop keeper" (a unique thing)
 * @param       flags execution flags (from the BUYSELL_XFLAG_ series)
 * @return      success or failure
 * <!-- 02/08/99 [YB] Original Programmer -->
*/
	public boolean	ExecuteBuySell(int thingGuid, int shopperGuid, int flags)
	{
		return(ExecuteBuySell(GetGUID(), thingGuid, shopperGuid, flags));
	}

/**
 * Adds a buy/sell (shop). This creates the shop and gives it its initial inventory.
 *
 * @param       filename the store filename
 * @param       thingGuid the guid of the "shop keeper" (or any unique store identifier, the shopkeeper's guid is just a very good choice :-))
 * @param		descriptionId the stringID of the shop name in the localised string table
 * @param       flags buy/sell flags
 * @param       buyRate how the shop prices are modified when you buy
 * @param       sellRate how the shop prices are modified when you sell
 * @param       sellFlags what you can sell to the shop (what types of items it accepts to buy)
 * @return      success or failure
 * <!-- 02/08/99 [YB] Original Programmer -->
*/
	public boolean	AddBuySell(String filename, int thingGuid, String descriptionId, int flags, float buyRate, float sellRate, int sellFlags)
	{
		return(AddBuySell(GetGUID(), filename, thingGuid, descriptionId, flags, buyRate, sellRate, sellFlags));
	}

/**
 * Begins a conversation (use only for conversations with BUYSELL_XFLAG_NOAUTOBEGIN).
 *
 * @param       flags the begin/end flags for the conversation (from the BUYSELL_BEFLAG_ series)
 * @return      success or failure
 * <!-- 02/08/99 [YB] Original Programmer -->
*/
	public boolean	BeginBuySell(int flags)
	{
		return(BeginBuySell(GetGUID(), flags));
	}

/**
 * Ends a conversation (use only for conversations with BUYSELL_XFLAG_NOAUTOEND).
 *
 * @param       flags the begin/end flags for the conversation (from the BUYSELL_BEFLAG_ series)
 * @return      success or failure
 * <!-- 02/08/99 [YB] Original Programmer -->
*/
	public boolean	EndBuySell(int flags)
	{
		return(EndBuySell(GetGUID(), flags));
	}


/**
 * Plays a video cutscene
 * 
 * @param       binkName name of the video file
 * @param       id an id that will be passed back 
 * <!-- 01/20/00 [YB] Original Programmer -->
*/	
	public boolean PlayVideo(String binkName, int id)
	{
		return(PlayVideo(GetGUID(), binkName, id));
	}

/**
 * Plays a video cutscene
 * 
 * @param       binkName name of the video file
 * <!-- 01/20/00 [YB] Original Programmer -->
*/	
	public boolean PlayVideo(String binkName)
	{
		return(PlayVideo(GetGUID(), binkName, 0));
	}

/**
 * Displays the text interface
 *
 * @param      ownerGuid the guid of the person reading the text
 * @param      thingGuid the guid of the thing that is read
 * @param      textName the name of the text to read
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public boolean	ExecuteText(int ownerGuid, int thingGuid, String textName)
	{
		return(ExecuteText(GetGUID(), ownerGuid, thingGuid, textName));
	}

/**
 * Turns the AI off (just a user friendly little shell function)
 *
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public void 	AIOff()
	{
		AISetMainFlags(AIMAIN_FLAG_PAUSED);
	}

/**
 * Turns the AI on (just a user friendly little shell function)
 *
 * @param      clientGuid the client to clear floaty text on
 * <!-- 06/09/00 [YB] Original Programmer -->
*/
	public void 	AIOn()
	{
		AIClearMainFlags(AIMAIN_FLAG_PAUSED);
	}

}


