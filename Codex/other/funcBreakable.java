/**
 * breakable Object script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author SGT
*/
 

public class funcBreakable extends Codex
{
	private CodexThing	_gib1;
	private CodexThing	_gib2;
	private CodexThing	_gib3;
	private CodexThing	_gib4;
	private CodexThing	_gib5;

	private float[]		velocity = new float[3];

	public static String _params[] = {"Gib 1", "Gib 2", "Gib 3", "Gib 4", "Gib 5", "Velocity X", "Velocity Y", "Velocity Z"};

	public funcBreakable(CodexThing gib1, CodexThing gib2, CodexThing gib3, CodexThing gib4, CodexThing gib5, float velX, float velY, float velZ)
	{
		float[] offset = new float[3];
		offset[VEC_X] = 0;
		offset[VEC_Y] = 0;
		offset[VEC_Z] = 0;

		_gib1 = new CodexThing(gib1.GetGUID());
		_gib2 = new CodexThing(gib2.GetGUID());
		_gib3 = new CodexThing(gib3.GetGUID());
		_gib4 = new CodexThing(gib4.GetGUID());
		_gib5 = new CodexThing(gib5.GetGUID());

		velocity[VEC_X] = velX;
		velocity[VEC_Y] = velY;
		velocity[VEC_Z] = velZ;

		CaptureThing(_gib1.GetGUID());
		CaptureThing(_gib2.GetGUID());
		CaptureThing(_gib3.GetGUID());
		CaptureThing(_gib4.GetGUID());
		CaptureThing(_gib5.GetGUID());
	}

	public void beginscene(int clientGuid, int captureID)
	{
		float[] tempVel = new float[3];

		tempVel[VEC_X] = velocity[VEC_X] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Y] = velocity[VEC_Y] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Z] = velocity[VEC_Z] ;// * (float)(0.50 - Math.random() * 1.00);
		_gib1.SetVelocity(tempVel);
		_gib1.SetMoveType(THING_MOVE_NONE);

		tempVel[VEC_X] = velocity[VEC_X] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Y] = velocity[VEC_Y] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Z] = velocity[VEC_Z] ;// * (float)(0.50 - Math.random() * 1.00);
		_gib2.SetVelocity(tempVel);
		_gib2.SetMoveType(THING_MOVE_NONE);

		tempVel[VEC_X] = velocity[VEC_X] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Y] = velocity[VEC_Y] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Z] = velocity[VEC_Z] ;// * (float)(0.50 - Math.random() * 1.00);
		_gib3.SetVelocity(tempVel);
		_gib3.SetMoveType(THING_MOVE_NONE);

		tempVel[VEC_X] = velocity[VEC_X] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Y] = velocity[VEC_Y] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Z] = velocity[VEC_Z] ;// * (float)(0.50 - Math.random() * 1.00);
		_gib4.SetVelocity(tempVel);
		_gib4.SetMoveType(THING_MOVE_NONE);

		tempVel[VEC_X] = velocity[VEC_X] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Y] = velocity[VEC_Y] ;// * (float)(0.50 - Math.random() * 1.00);
		tempVel[VEC_Z] = velocity[VEC_Z] ;// * (float)(0.50 - Math.random() * 1.00);
		_gib5.SetVelocity(tempVel);
		_gib5.SetMoveType(THING_MOVE_NONE);

		// tweak velocity at random here
	}

	public void	triggered(int triggeredGUID, int triggererGUID, int triggerID, float p0, float p1, float p2, float p3, int captureID)
	{
		_gib1.SetMoveType(THING_MOVE_PHYSICS);
		_gib2.SetMoveType(THING_MOVE_PHYSICS);
		_gib3.SetMoveType(THING_MOVE_PHYSICS);
		_gib4.SetMoveType(THING_MOVE_PHYSICS);
		_gib5.SetMoveType(THING_MOVE_PHYSICS);
	}
}



