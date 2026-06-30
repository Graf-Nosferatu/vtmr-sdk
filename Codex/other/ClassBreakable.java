/**
 * breakable Object script
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author BM
*/
 

public class ClassBreakable extends Codex
{
	private CodexThing	_gib1;
	private CodexThing	_gib2;
	private CodexThing	_gib3;
	private CodexThing	_gib4;
	private CodexThing	_gib5;

	private float[]		velocity = new float[3];
	private float[]		rotVelocity = new float[3];

	private boolean		_bTriggered = false;

	public static String _params[] = {"Gib 1", "Gib 2", "Gib 3", "Gib 4", "Gib 5", "Velocity X", "Velocity Y", "Velocity Z",
										"Rotational Velocity X", "Rotational Velocity Y", "Rotational Velocity Z"};

	public ClassBreakable(CodexThing gib1, CodexThing gib2, CodexThing gib3, CodexThing gib4, CodexThing gib5, 
						float velX, float velY, float velZ, float rotVelX, float rotVelY, float rotVelZ)
	{
		_gib1 = new CodexThing(gib1.GetGUID());
		_gib2 = new CodexThing(gib2.GetGUID());
		_gib3 = new CodexThing(gib3.GetGUID());
		_gib4 = new CodexThing(gib4.GetGUID());
		_gib5 = new CodexThing(gib5.GetGUID());

		velocity[VEC_X] = velX;
		velocity[VEC_Y] = velY;
		velocity[VEC_Z] = velZ;

		rotVelocity[VEC_X] = rotVelX;
		rotVelocity[VEC_Y] = rotVelY;
		rotVelocity[VEC_Z] = rotVelZ;
	}

	public void beginscene(int clientGuid, int captureID)
	{
		if(!_bTriggered)
		{
			float[] tempVel = new float[3];
			float[] tempRotVel = new float[3];

			// this takes the value the user entered, multiplies it by Math.random(), and then
			// multiplies it by either -1 or 1 (for a random positive/negative factor)

			//               original value  *         random value   *                         factor

			tempVel[VEC_X] = velocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Y] = velocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Z] = velocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib1.SetVelocity(tempVel);

			tempRotVel[VEC_X] = rotVelocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Y] = rotVelocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Z] = rotVelocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib1.SetRotVelocity(tempRotVel);

			_gib1.SetPhysicsFlags(0x8);

			tempVel[VEC_X] = velocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Y] = velocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Z] = velocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib2.SetVelocity(tempVel);

			tempRotVel[VEC_X] = rotVelocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Y] = rotVelocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Z] = rotVelocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib2.SetRotVelocity(tempRotVel);

			_gib2.SetPhysicsFlags(0x8);

			tempVel[VEC_X] = velocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Y] = velocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Z] = velocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib3.SetVelocity(tempVel);

			tempRotVel[VEC_X] = rotVelocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Y] = rotVelocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Z] = rotVelocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib3.SetRotVelocity(tempRotVel);

			_gib3.SetPhysicsFlags(0x8);

			tempVel[VEC_X] = velocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Y] = velocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Z] = velocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib4.SetVelocity(tempVel);
			
			tempRotVel[VEC_X] = rotVelocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Y] = rotVelocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Z] = rotVelocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib4.SetRotVelocity(tempRotVel);

			_gib4.SetPhysicsFlags(0x8);

			tempVel[VEC_X] = velocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Y] = velocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempVel[VEC_Z] = velocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib5.SetVelocity(tempVel);
			
			tempRotVel[VEC_X] = rotVelocity[VEC_X] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Y] = rotVelocity[VEC_Y] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			tempRotVel[VEC_Z] = rotVelocity[VEC_Z] * (float)(Math.random()) * ((Math.random() < 0.5) ? -1 : 1);
			_gib5.SetRotVelocity(tempRotVel);

			_gib5.SetPhysicsFlags(0x8);

			// tweak velocity at random here
		}
	}

	public void	triggered(int triggeredGUID, int triggererGUID, int triggerID, float p0, float p1, float p2, float p3, int captureID)
	{
		_bTriggered = true;

		_gib1.ClearPhysicsFlags(0x8);
		_gib2.ClearPhysicsFlags(0x8);
		_gib3.ClearPhysicsFlags(0x8);
		_gib4.ClearPhysicsFlags(0x8);
		_gib5.ClearPhysicsFlags(0x8);
	}
}



