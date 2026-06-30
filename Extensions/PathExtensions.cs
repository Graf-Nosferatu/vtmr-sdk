namespace System.IO;

public static class PathExtensions
{
	extension( Path )
	{
		public static String Create( params String[] paths )
		{
			String path = Path.Combine( paths );
			String? directory = Path.GetDirectoryName( path );
			if( !String.IsNullOrEmpty( directory ) )
			{
				if( !Directory.Exists( directory ) )
				{
					Directory.CreateDirectory( directory );
				}

				return path;
			}

			throw new ArgumentException( null, nameof( paths ) );
		}
	}
}