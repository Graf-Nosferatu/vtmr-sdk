using System;
using System.Globalization;

namespace Sdk.Extensions;

public static class Saver
{
	public static String SaveEnum<TEnum>( TEnum value ) where TEnum : struct, Enum
	{
		bool isFlags = typeof(TEnum).IsDefined( typeof(FlagsAttribute), false );
		Int32 valueAsInt32 = (Int32)(Object)value;

		return isFlags ? SaveAsHexInt32( valueAsInt32 ) : SaveInt32( valueAsInt32 );
	}


	private static String SaveAsHexInt32( Int32 value )
	{
		return $"0x{value:X}";
	}


	public static String SaveInt32( Int32 value )
	{
		return $"{value}";
	}


	public static String SaveSingle( Single value )
	{
		return value.ToString( CultureInfo.InvariantCulture );
	}
}