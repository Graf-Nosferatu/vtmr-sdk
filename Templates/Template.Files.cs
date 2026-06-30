using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Numerics;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text;
using Sdk.Extensions;

using TagProperty = (Sdk.Templates.TagAttribute Tag, System.Func<System.Object?, System.Object?> GetValue);
using TagValue = (Sdk.Templates.Tag Tag, System.Object? Value);

namespace Sdk.Templates;

partial class Template
{
	public const String DirectoryName = "misc\\templates";


	public static File<Template> Global { get; } = new( "global.not" );

	public static File<Template> DarkAges { get; } = new( "darkAges.not" );

	public static File<Template> ModernDay { get; } = new( "modernDay.not" );

	public static File<Template> Multiplayer { get; } = new( "multiplayer.not" );


	private static readonly TagProperty[] TagProperties = GetTagProperties();
	private static TagProperty[] GetTagProperties()
	{
		List<(TagAttribute Tag, Func<Object?, Object?> GetValue)> tags = [];
		foreach( var property in typeof( Template ).GetProperties() )
		{
			TagAttribute? tag = property.GetCustomAttribute<TagAttribute>();
			if( tag != null )
			{
				tags.Add( (tag, property.GetValue) );
			}
		}

		return [..tags.OrderBy( t => t.Tag.Order )];
	}
	private static TagValue[] GetTagValues( Template template )
	{
		return [..TagProperties.Select( p => (Tag: p.Tag.Id, Value: p.GetValue( template )) ).Where( tv => tv.Value != null )];
	}


	public static void Save()
	{
		foreach( var file in new[]{ Global, DarkAges, ModernDay, Multiplayer } )
		{
			String path = Path.Create( Sdk.ModPath, DirectoryName, file.FileName );
			Int32 numTemplates = file.Content.Count;
			using BinaryWriter writer = new( File.Create( path ) );
			writer.Write( [78, 79, 84, 16, 1, 0, 0, 0] );
			writer.Write( numTemplates );
			Int32 offset = 8/*header*/ + 4/*numTemplates*/ + (numTemplates * (32/*name*/ + 4/*offset*/));
			foreach( Template template in file.Content )
			{
				SaveString( writer, template.Name );
				writer.Write( offset );
				offset += CalculateLength( template );
			}
			foreach( Template template in file.Content )
			{
				SaveTemplate( writer, template );
			}
		}
	}


	private static Int32 CalculateLength( Template template )
	{
		TagValue[] tags = GetTagValues( template );
		Int32 numTags = tags.Length;
		Int32 tagsLength = numTags * (4/*tag*/ + 32/*value*/);
		Int32 commentLength = (template.Comment?.Length ?? 0) + 1;

		return 4/*numTags*/ + tagsLength + 4/*commentLength*/ + commentLength;
	}


	private static void SaveTemplate( BinaryWriter writer, Template template )
	{
		TagValue[] tags = GetTagValues( template );
		writer.Write( tags.Length );
		foreach( var tag in tags )
		{
			SaveTag( writer, tag );
		}
		SaveComment( writer, template.Comment );
	}


	private static void SaveTag( BinaryWriter writer, TagValue tagValue )
	{
		writer.Write( (Int32)tagValue.Tag );
		switch( tagValue.Value )
		{
			case String value:
				SaveString( writer, value );
				break;

			case Int32 value:
				SaveInt32( writer, value );
				break;

			case Enum value:
				SaveInt32( writer, (Int32)(Object)value );
				break;

			case Single value:
				SaveSingle( writer, value );
				break;

			case Vector3 value:
				SaveVector3( writer, value );
				break;

			case Color value:
				SaveVector3( writer, new( value.R, value.G, value.B ) );
				break;
		}
	}


	private static Byte[] buffer = new Byte[32];
	private static Byte EOL = 0;


	private static void SaveString( BinaryWriter writer, String value )
	{
		Encoding.ASCII.GetBytes( value, 0, value.Length, buffer, 0 );
		buffer[value.Length] = EOL;

		WriteBuffer( writer );
	}


	private static void SaveInt32( BinaryWriter writer, Int32 value )
	{
		Span<Int32> span = MemoryMarshal.Cast<Byte, Int32>( buffer.AsSpan() );
		span[0] = value;

		WriteBuffer( writer );
	}


	private static void SaveSingle( BinaryWriter writer, Single value )
	{
		Span<Single> span = MemoryMarshal.Cast<Byte, Single>( buffer.AsSpan() );
		span[0] = value;

		WriteBuffer( writer );
	}


	private static void SaveVector3( BinaryWriter writer, Vector3 value )
	{
		Span<Single> span = MemoryMarshal.Cast<Byte, Single>( buffer.AsSpan() );
		span[0] = value.X;
		span[1] = value.Y;
		span[2] = value.Z;

		WriteBuffer( writer );
	}


	private static void WriteBuffer( BinaryWriter writer )
	{
		writer.Write( buffer );
	}


	private static void SaveComment( BinaryWriter writer, String? comment )
	{
		Int32 length = (comment?.Length ?? 0) + 1/*EOL*/;
		Byte[] buffer = new Byte[length];

		if( !String.IsNullOrEmpty( comment ) ) {
			Encoding.ASCII.GetBytes( comment, 0, length - 1, buffer, 0 );
		}

		writer.Write( length );
		writer.Write( buffer );
	}
}