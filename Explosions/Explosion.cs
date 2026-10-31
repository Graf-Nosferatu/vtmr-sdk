using System;
using System.Collections;
using System.Collections.Generic;
using System.Drawing;
using System.Runtime.CompilerServices;
using Sdk.Templates;
using static Sdk.Extensions.CallerArgumentExtensions;
using static Sdk.Extensions.Saver;

namespace Sdk.Explosions;

public partial class Explosion : IEnumerable<String>
{
	private IEnumerable<String> commands;


	public Explosion( params String[] commands )
	{
		this.commands = commands;
	}


	public IEnumerator<String> GetEnumerator()
	{
		return commands.GetEnumerator();
	}

	IEnumerator IEnumerable.GetEnumerator()
	{
		return GetEnumerator();
	}


	public static String NOP( Int32 time )
	{
		return $"{time} NOP";
	}


	/// <summary>
	/// Создаёт заданное количество частиц
	/// </summary>
	/// <param name="time"></param>
	/// <param name="repeat">Количество частиц</param>
	/// <param name="chance">Шанс создания частицы (от 0.0 до 1.0)</param>
	/// <param name="timer">Время жизни частицы</param>
	/// <param name="template">Шаблон представляющий частицу</param>
	/// <param name="distance">Длина вектора</param>
	/// <param name="offset">Направление вектора x,y,z. Если перед координатой указать r, то будет использовано случайное значение от 0 до значения после r</param>
	/// <param name="templateName">Имя шаблона представляющего частицу</param>
	public static String CREATE( Int32 time, Int32 repeat, Single chance, Int32 timer, Template? template, String distance, String offset, [CallerArgumentExpression( nameof( template ) )] String templateName = "" )
	{
		offset = offset.Replace( ',', ' ' );

		return $"{time} CREATE {repeat} {SaveSingle(chance)} {timer} {NameOf( templateName )} {distance} {offset}";
	}


	/// <summary>
	/// Задаёт урон определённого типа по области
	/// </summary>
	/// <param name="time"></param>
	/// <param name="damage">Минимальный и максимальный урон. Если минимальный больше максимального, то рандома не будет</param>
	/// <param name="damageType">Тип урона</param>
	/// <param name="radius">Радиус области где действует урон</param>
	/// <param name="timer">
	/// Если задано 0, то для нанесения урона необходимо вызвать инструкцию APPLYDAMAGE.
	/// Иначе задержка следующего удара до time инструкции NOP. Первый удар вызывается в time текущей инструкции
	/// </param>
	public static String SETDAMAGE( Int32 time, MinMax<Int32> damage, ENUM_WEAPON_DAMAGETYPE damageType, Int32 radius, Int32 timer = 0 )
	{
		return $"{time} SETDAMAGE {damage.Min} {damage.Max} {timer} {(Int32)damageType} {radius}";
	}


	public static String APPLYDAMAGE( Int32 time )
	{
		return $"{time} APPLYDAMAGE";
	}


	public static String DYNAON( Int32 time )
	{
		return $"{time} DYNAON";
	}


	public static String DYNAOFF( Int32 time )
	{
		return $"{time} DYNAOFF";
	}


	public static String DYNAINTENSITY( Int32 time, Single value )
	{
		return $"{time} DYNAINTENSITY INSTANT {SaveSingle(value)}";
	}


	public static String DYNAINTENSITY( Int32 time, Single value, Int32 timer )
	{
		return $"{time} DYNAINTENSITY RAMP {SaveSingle(value)} {timer}";
	}


	public static String DYNAINTENSITY( Int32 time, Single value, Int32 timer, Int32 peakValue, Int32 peakTimer )
	{
		return $"{time} DYNAINTENSITY GAUSS {SaveSingle(value)} {timer} {peakValue} {peakTimer}";
	}


	public static String DYNARADIUS( Int32 time, Single value )
	{
		return $"{time} DYNARADIUS INSTANT {SaveSingle(value)}";
	}


	public static String DYNARADIUS( Int32 time, Single value, Int32 timer )
	{
		return $"{time} DYNARADIUS RAMP {SaveSingle(value)} {timer}";
	}


	public static String DYNACOLOR( Int32 time, Color color )
	{
		return $"{time} DYNACOLOR INSTANT {color.R} {color.G} {color.B}";
	}


	public static String DYNACOLOR( Int32 time, Color color, Int32 timer )
	{
		return $"{time} DYNACOLOR RAMP {color.R} {color.G} {color.B} {timer}";
	}


	public static String MODELALPHA( Int32 time, Single value )
	{
		return $"{time} MODELALPHA INSTANT {SaveSingle(value)}";
	}


	public static String MODELALPHA( Int32 time, Single value, Int32 timer )
	{
		return $"{time} MODELALPHA RAMP {SaveSingle(value)} {timer}";
	}


	public static String MODELSCALE( Int32 time, Single value )
	{
		return $"{time} MODELSCALE INSTANT {SaveSingle(value)}";
	}


	public static String MODELSCALE( Int32 time, Single value, Int32 timer )
	{
		return $"{time} MODELSCALE RAMP {SaveSingle(value)} {timer}";
	}


	public static String WALLMARK( Int32 time, Int32 value, Int32 timer )
	{
		return $"{time} WALLMARK {value} {timer}";
	}
}