package org.toxsoft.skf.ggprefs.lib;

import static org.toxsoft.core.tslib.av.EAtomicType.*;
import static org.toxsoft.core.tslib.av.impl.AvUtils.*;
import static org.toxsoft.core.tslib.av.metainfo.IAvMetaConstants.*;
import static org.toxsoft.skf.ggprefs.lib.ISkResources.*;

import org.toxsoft.core.tslib.av.IAtomicValue;
import org.toxsoft.core.tslib.av.impl.DataDef;
import org.toxsoft.core.tslib.av.metainfo.IDataDef;
import org.toxsoft.core.tslib.bricks.strid.coll.IStridablesList;
import org.toxsoft.core.tslib.gw.gwid.Gwid;
import org.toxsoft.core.tslib.gw.skid.Skid;

/**
 * Константы, используемые настройками.
 * <p>
 * <h3>Введение</h3><br>
 * Данная служба предоставляет возможность описывать опции (параметры) <b>настроек GUI</b> приложения, связанные с
 * зеленым миром (предметной областью).
 * <p>
 * Звучит как-то непонятно, но суть в общем-то несложная. Простой пример - в метро каждую линию принятно показыватьсвоим
 * цветом. Вот и получается опция "Цвет линии", привязанный к объекту "линия метро". <b>Описание</b> опции одно, а
 * <b>значение</b> опции свой для каждой линии.
 * <p>
 * Надо не путать настройки GUI с НСИ. НСИ - понятие предметной области, атрибуты и связи НСИ определяются предметной
 * областью фактически являются частью описания системы. Настройки же GUI обслуживают программный код визуализации, и
 * определяются использующих кодом, создаются программистом и активно изменяются по мере работы под кодом. Грубо говоря,
 * нет кода визуализации - не существует настроек GUI. Но когда они существуют, значения опции настроек хранятся в
 * сервере с привязкой к сущностям предметной области. Этим они и отличаются от обычных настроек программы (которые
 * хранятся на компьютере).
 * <p>
 * <h3>Основные понятия</h3><br>
 * <ul>
 * <li><b>опция (option)</b> - состоит из <b>описания</b> и <b>значения</b>. Описанием опции является {@link IDataDef},
 * значением - {@link IAtomicValue}. Одно описание опции, в зависимости от того, к чему она <b>привязана</b>, может
 * порождать много значении. Например, опция "Цвет линии" порождает по одному значению на каждый объект линии метро.
 * Напомним, что опции имеют понятие "значение по умолчанию", которое применяется, если программист или пользователь
 * явным образом не задал значение конкретной опции;</li>
 * <li><b>привязка</b> - связь между опцией и сущностью зеленого мира. Опция может быть привязана либо к классу (в таком
 * случае, есть одно описание и много значении опции, по одному на объект класса), либо к объекту (в таком случае будет
 * одно описание и одно значение опции);</li>
 * <li><b>редактирование значении</b> - осуществляется пользователем с помощью специального диалога (средствами класса
 * {GuiGwPrefsUtils}). При редактировании множество опции могут быть сгруппированы на усмотрение разработчика;</li>
 * <li><b>раздел настроек</b> - служба настроек {@link ISkGuiGwPrefsService} делается как расширенный (sysext) Sk-сервис
 * общего назначения. Надо понимать, что в одной программе могут быть разные GUI модули, имеющие собственную
 * визуализацию (например в SITROL могут быть это редактор ГДП, редактор ночных расстановок, редактор карты метро).
 * Чтобы избежать конфликтов имен и перегруженности диалогов редактирования "чужими" опциями, вводится понятие раздела
 * настроек {@link IGuiGwPrefsSection}. Каждая визуализация должна создать и использовать свой раздел (аналогично
 * разделам в НСИ).</li>
 * </ul>
 * <p>
 * <h3>Использование программистом</h3><br>
 * Методика использования программистом включает в себя следующие шаги:
 * <ul>
 * <li>свой раздел - создать свой раздел {@link IGuiGwPrefsSection} и в дальнейшем работать только с ним;</li>
 * <li>определить опции - по мере написания кода визуализации, определять свои опции в виде констант типа
 * {@link IDataDef};</li>
 * <li>группы описаний опции привязывать к зеленому миру методом
 * {@link IGuiGwPrefsSection#bindOptions(Gwid, IStridablesList)};</li>
 * <li>использовать опции - для конкретного объекта получить набор опции методом
 * {@link IGuiGwPrefsSection#getOptions(Skid)} и оттуда извлекать значения опции;</li>
 * <li>отрабатывать правки пользователя - подписаться на сообщения через {@link IGuiGwPrefsSection#eventer()};</li>
 * <li>вызвать диалог редактирования настроек пользователем - из общего или локального мен, кнопками или любым другим
 * способом можно вызвать редактор настроек как для конкретного объекта, так и списка объектов средствами класса
 * {GuiGwPrefsUtils}.</li>
 * </ul>
 *
 * @author goga
 */
public interface IGuiGwPrefsConstants {

  /**
   * Предопределенный идентификатор настроек, привязанных к компьютеру, на которой выпоняется программа.
   * <p>
   * FIXME это некий хак пока, в системе надо иметь предопределенный объект, означающий "This PC" :)
   */
  Skid SKID_THIS_PC = new Skid( "PC", "ThisOne" ); //$NON-NLS-1$//$NON-NLS-2$

  /**
   * GWID версия {@link #SKID_THIS_PC}.
   */
  Gwid GWID_THIS_PC = Gwid.createObj( SKID_THIS_PC );

  /**
   * Предопределенный идентификатор настроек, общих для все системы.
   * <p>
   * FIXME это некий хак пока, в системе надо иметь предопределенный объект, означающий "This System" :)
   */
  Skid SKID_SYSTEM = new Skid( "System", "ThisOne" ); //$NON-NLS-1$ //$NON-NLS-2$

  /**
   * GWID версия {@link #SKID_SYSTEM}.
   */
  Gwid GWID_SYSTEM = Gwid.createObj( SKID_SYSTEM );

  /**
   * Корневой узел дерева настроек.
   *
   * @see IGuiGwPrefsConstants#OPDEF_TREE_PATH1
   */
  String TREE_PATH1_ROOT = "/"; //$NON-NLS-1$

  /**
   * Идентификатор параметра {@link #OPDEF_TREE_PATH1}.
   */
  String OPID_TREE_PATH1 = "GuiGwPrefs.TreePath1"; //$NON-NLS-1$

  /**
   * Параметр задает путь для отображения опции в дереве настроек в диалоге редактирования.
   * <p>
   * Это параметр {@link IDataDef#params()} для описании опции GUI.
   * <p>
   * Путь расположения опции в дереве настроек задается в виде "/имя узла/подузел узла/". Количество и глбина вложения
   * может быть произвольным. Путь "/" ({@link #TREE_PATH1_ROOT}) располагает опцию в корне дерева, то есть, если у всех
   * опции такой путь, то дерево настроек вырождается в список. Точнее, в пустой список - ведь опции не отображатся в
   * дереве - когда в дереве выбирается узел, опции этого узла отображатся справа от дерева в панели редактирования
   * списка опции.
   */
  IDataDef OPDEF_TREE_PATH1 = DataDef.create( OPID_TREE_PATH1, STRING, //
      TSID_NAME, STR_N_TREE_PATH1, //
      TSID_DESCRIPTION, STR_D_TREE_PATH1, //
      TSID_DEFAULT_VALUE, avStr( TREE_PATH1_ROOT ) //
  );

}
