/**
 * RpgConversation script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
 * @author YB
*/

public class RpgConversation extends Codex
{
	private String		_conversationName;
	private String		_fileName;
	private int			_flags;

	public static String _params[] =  {	"Conversation name", 
										"Filename",
										"Execution flags;0" };

	public RpgConversation(String conversationName, String fileName, int flags)
	{
		_conversationName	= conversationName;
		_fileName			= fileName;
		_flags				= flags;
	}

	public void clicked(int guid, int clickerGuid, int captureID)
	{
		ExecuteConversation(clickerGuid, 0, _conversationName, _fileName, _flags);
	}
}
