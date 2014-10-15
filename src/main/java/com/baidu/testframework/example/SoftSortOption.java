/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.baidu.testframework.example;


import org.apache.thrift.TEnum;

public enum SoftSortOption implements TEnum {
  Default(0),
  f_idASC(1),
  f_idDESC(2),
  f_introDESC(3),
  f_infoupt_timeASC(4),
  f_infoupt_timeDESC(5),
  f_update_timeASC(6),
  f_update_timeDESC(7),
  f_add_timeASC(8),
  f_add_timeDESC(9),
  f_downnumASC(10),
  f_downnumDESC(11),
  f_downnum_weekDESC(12),
  f_downnum_monthDESC(13),
  f_diggnumDESC(14),
  f_diggnum_weekDESC(15),
  f_burynumDESC(16),
  f_burynum_weekDESC(17),
  f_sizeASC(18),
  f_sizeDESC(19),
  f_nameASC(20),
  f_nameDESC(21),
  f_sellnum(22),
  f_sellnumDESC(23),
  SortNumberASC(24),
  SortNumberDESC(25),
  f_downnum_dayASC(26),
  f_downnum_dayDESC(27),
  f_increment_weekASC(28),
  f_increment_weekDESC(29),
  f_avgscoreASC(30),
  f_avgscoreDESC(31),
  f_sortnumASC(32),
  f_sortnumDESC(33),
  f_authorASC(34),
  f_authorDESC(35),
  f_infoupt_intro_timeDESC(36),
  StartTimeASC(37),
  StartTimeDESC(38),
  EndTimeASC(39),
  EndTimeDESC(40),
  f_publish_timeASC(41),
  f_publish_timeDESC(42);

  private final int value;

  private SoftSortOption(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static SoftSortOption findByValue(int value) { 
    switch (value) {
      case 0:
        return Default;
      case 1:
        return f_idASC;
      case 2:
        return f_idDESC;
      case 3:
        return f_introDESC;
      case 4:
        return f_infoupt_timeASC;
      case 5:
        return f_infoupt_timeDESC;
      case 6:
        return f_update_timeASC;
      case 7:
        return f_update_timeDESC;
      case 8:
        return f_add_timeASC;
      case 9:
        return f_add_timeDESC;
      case 10:
        return f_downnumASC;
      case 11:
        return f_downnumDESC;
      case 12:
        return f_downnum_weekDESC;
      case 13:
        return f_downnum_monthDESC;
      case 14:
        return f_diggnumDESC;
      case 15:
        return f_diggnum_weekDESC;
      case 16:
        return f_burynumDESC;
      case 17:
        return f_burynum_weekDESC;
      case 18:
        return f_sizeASC;
      case 19:
        return f_sizeDESC;
      case 20:
        return f_nameASC;
      case 21:
        return f_nameDESC;
      case 22:
        return f_sellnum;
      case 23:
        return f_sellnumDESC;
      case 24:
        return SortNumberASC;
      case 25:
        return SortNumberDESC;
      case 26:
        return f_downnum_dayASC;
      case 27:
        return f_downnum_dayDESC;
      case 28:
        return f_increment_weekASC;
      case 29:
        return f_increment_weekDESC;
      case 30:
        return f_avgscoreASC;
      case 31:
        return f_avgscoreDESC;
      case 32:
        return f_sortnumASC;
      case 33:
        return f_sortnumDESC;
      case 34:
        return f_authorASC;
      case 35:
        return f_authorDESC;
      case 36:
        return f_infoupt_intro_timeDESC;
      case 37:
        return StartTimeASC;
      case 38:
        return StartTimeDESC;
      case 39:
        return EndTimeASC;
      case 40:
        return EndTimeDESC;
      case 41:
        return f_publish_timeASC;
      case 42:
        return f_publish_timeDESC;
      default:
        return null;
    }
  }
}
