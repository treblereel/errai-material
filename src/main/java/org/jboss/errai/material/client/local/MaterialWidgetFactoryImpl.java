
/*
 *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.jboss.errai.material.client.local;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.enterprise.context.ApplicationScoped;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Alignment;
import gwt.material.design.client.constants.Axis;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.CenterOn;
import gwt.material.design.client.constants.CollapsibleType;
import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.DatePickerContainer;
import gwt.material.design.client.constants.DatePickerLanguage;
import gwt.material.design.client.constants.Edge;
import gwt.material.design.client.constants.FABType;
import gwt.material.design.client.constants.Flex;
import gwt.material.design.client.constants.FlexAlignContent;
import gwt.material.design.client.constants.FlexAlignSelf;
import gwt.material.design.client.constants.FlexDirection;
import gwt.material.design.client.constants.FlexWrap;
import gwt.material.design.client.constants.FooterType;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.ImageType;
import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.constants.LoaderSize;
import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.constants.Orientation;
import gwt.material.design.client.constants.ProgressType;
import gwt.material.design.client.constants.ShowOn;
import gwt.material.design.client.constants.SpinnerColor;
import gwt.material.design.client.constants.TabType;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialBreadcrumb;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialCardReveal;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDivider;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialFABList;
import gwt.material.design.client.ui.MaterialFloatBox;
import gwt.material.design.client.ui.MaterialFooter;
import gwt.material.design.client.ui.MaterialFooterCopyright;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialHelpBlock;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialListValueBox;
import gwt.material.design.client.ui.MaterialLongBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialModalFooter;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBarShrink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialNavContent;
import gwt.material.design.client.ui.MaterialNavMenu;
import gwt.material.design.client.ui.MaterialNavSection;
import gwt.material.design.client.ui.MaterialNoResult;
import gwt.material.design.client.ui.MaterialPager;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialParallax;
import gwt.material.design.client.ui.MaterialPreLoader;
import gwt.material.design.client.ui.MaterialProgress;
import gwt.material.design.client.ui.MaterialRange;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialScrollspy;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialSearchResult;
import gwt.material.design.client.ui.MaterialSection;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialSideNavCard;
import gwt.material.design.client.ui.MaterialSideNavContent;
import gwt.material.design.client.ui.MaterialSideNavDrawer;
import gwt.material.design.client.ui.MaterialSideNavMini;
import gwt.material.design.client.ui.MaterialSideNavPush;
import gwt.material.design.client.ui.MaterialSlideCaption;
import gwt.material.design.client.ui.MaterialSlideItem;
import gwt.material.design.client.ui.MaterialSlider;
import gwt.material.design.client.ui.MaterialSpinner;
import gwt.material.design.client.ui.MaterialSplashScreen;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialTopNav;
import gwt.material.design.client.ui.MaterialVideo;
import gwt.material.design.client.ui.MaterialWeather;
import org.jboss.errai.material.client.local.MaterialMethodDefinition;
import org.jboss.errai.material.client.local.MaterialWidgetDefinition;
import org.jboss.errai.material.client.local.MaterialWidgetFactory;

@ApplicationScoped
public class MaterialWidgetFactoryImpl
    implements MaterialWidgetFactory
{

    private Map<String, MaterialWidgetDefinition> widgets = new HashMap<String, MaterialWidgetDefinition>();
    private Map<String, MaterialMethodDefinition> defaultMethods = new HashMap<String, MaterialMethodDefinition>();

    public MaterialWidgetFactoryImpl() {
        addMaterialWidget();
        addMaterialAnchorButton();
        addMaterialBadge();
        addMaterialBreadcrumb();
        addMaterialButton();
        addMaterialCard();
        addMaterialCardAction();
        addMaterialCardContent();
        addMaterialCardImage();
        addMaterialCardReveal();
        addMaterialCardTitle();
        addMaterialChip();
        addMaterialCollapsible();
        addMaterialCollapsibleBody();
        addMaterialCollapsibleHeader();
        addMaterialCollapsibleItem();
        addMaterialCollection();
        addMaterialCollectionItem();
        addMaterialCollectionSecondary();
        addMaterialColumn();
        addMaterialContainer();
        addMaterialDatePicker();
        addMaterialDivider();
        addMaterialDoubleBox();
        addMaterialDropDown();
        addMaterialFAB();
        addMaterialFABList();
        addMaterialFloatBox();
        addMaterialFooter();
        addMaterialFooterCopyright();
        addMaterialHeader();
        addMaterialHelpBlock();
        addMaterialIcon();
        addMaterialImage();
        addMaterialInput();
        addMaterialIntegerBox();
        addMaterialLabel();
        addMaterialLink();
        addMaterialListBox();
        addMaterialListValueBox();
        addMaterialLongBox();
        addMaterialModal();
        addMaterialModalContent();
        addMaterialModalFooter();
        addMaterialNavBar();
        addMaterialNavBarShrink();
        addMaterialNavBrand();
        addMaterialNavContent();
        addMaterialNavMenu();
        addMaterialNavSection();
        addMaterialNoResult();
        addMaterialPager();
        addMaterialPanel();
        addMaterialParallax();
        addMaterialPreLoader();
        addMaterialProgress();
        addMaterialRange();
        addMaterialRow();
        addMaterialScrollspy();
        addMaterialSearch();
        addMaterialSearchResult();
        addMaterialSection();
        addMaterialSideNav();
        addMaterialSideNavCard();
        addMaterialSideNavContent();
        addMaterialSideNavDrawer();
        addMaterialSideNavMini();
        addMaterialSideNavPush();
        addMaterialSlideCaption();
        addMaterialSlideItem();
        addMaterialSlider();
        addMaterialSpinner();
        addMaterialSplashScreen();
        addMaterialSwitch();
        addMaterialTab();
        addMaterialTabItem();
        addMaterialTextArea();
        addMaterialTextBox();
        addMaterialTitle();
        addMaterialTopNav();
        addMaterialVideo();
        addMaterialWeather();
    }

    @Override
    public java.util.Optional<MaterialWidget> invoke(Element tagged, Element param, java.util.Set<Widget> templateFieldsMap) {
        MaterialWidget result = null;
        String tag = tagged.getTagName().toLowerCase().replaceAll("-", "");
        tag = tag.toLowerCase().replaceAll("-", "");
        if (tag.equals("materialwidget")) {
            result = new MaterialWidget();
        }
        if (tag.equals("materialanchorbutton")) {
            result = new MaterialAnchorButton();
        }
        if (tag.equals("materialbadge")) {
            result = new MaterialBadge();
        }
        if (tag.equals("materialbreadcrumb")) {
            result = new MaterialBreadcrumb();
        }
        if (tag.equals("materialbutton")) {
            result = new MaterialButton();
        }
        if (tag.equals("materialcard")) {
            result = new MaterialCard();
        }
        if (tag.equals("materialcardaction")) {
            result = new MaterialCardAction();
        }
        if (tag.equals("materialcardcontent")) {
            result = new MaterialCardContent();
        }
        if (tag.equals("materialcardimage")) {
            result = new MaterialCardImage();
        }
        if (tag.equals("materialcardreveal")) {
            result = new MaterialCardReveal();
        }
        if (tag.equals("materialcardtitle")) {
            result = new MaterialCardTitle();
        }
        if (tag.equals("materialchip")) {
            result = new MaterialChip();
        }
        if (tag.equals("materialcollapsible")) {
            result = new MaterialCollapsible();
        }
        if (tag.equals("materialcollapsiblebody")) {
            result = new MaterialCollapsibleBody();
        }
        if (tag.equals("materialcollapsibleheader")) {
            result = new MaterialCollapsibleHeader();
        }
        if (tag.equals("materialcollapsibleitem")) {
            result = new MaterialCollapsibleItem();
        }
        if (tag.equals("materialcollection")) {
            result = new MaterialCollection();
        }
        if (tag.equals("materialcollectionitem")) {
            result = new MaterialCollectionItem();
        }
        if (tag.equals("materialcollectionsecondary")) {
            result = new MaterialCollectionSecondary();
        }
        if (tag.equals("materialcolumn")) {
            result = new MaterialColumn();
        }
        if (tag.equals("materialcontainer")) {
            result = new MaterialContainer();
        }
        if (tag.equals("materialdatepicker")) {
            result = new MaterialDatePicker();
        }
        if (tag.equals("materialdivider")) {
            result = new MaterialDivider();
        }
        if (tag.equals("materialdoublebox")) {
            result = new MaterialDoubleBox();
        }
        if (tag.equals("materialdropdown")) {
            result = new MaterialDropDown();
        }
        if (tag.equals("materialfab")) {
            result = new MaterialFAB();
        }
        if (tag.equals("materialfablist")) {
            result = new MaterialFABList();
        }
        if (tag.equals("materialfloatbox")) {
            result = new MaterialFloatBox();
        }
        if (tag.equals("materialfooter")) {
            result = new MaterialFooter();
        }
        if (tag.equals("materialfootercopyright")) {
            result = new MaterialFooterCopyright();
        }
        if (tag.equals("materialheader")) {
            result = new MaterialHeader();
        }
        if (tag.equals("materialhelpblock")) {
            result = new MaterialHelpBlock();
        }
        if (tag.equals("materialicon")) {
            result = new MaterialIcon();
        }
        if (tag.equals("materialimage")) {
            result = new MaterialImage();
        }
        if (tag.equals("materialinput")) {
            result = new MaterialInput();
        }
        if (tag.equals("materialintegerbox")) {
            result = new MaterialIntegerBox();
        }
        if (tag.equals("materiallabel")) {
            result = new MaterialLabel();
        }
        if (tag.equals("materiallink")) {
            result = new MaterialLink();
            result.getElement().setAttribute("href","#ololop");
        }
        if (tag.equals("materiallistbox")) {
            result = new MaterialListBox();
        }
        if (tag.equals("materiallistvaluebox")) {
            result = new MaterialListValueBox();
        }
        if (tag.equals("materiallongbox")) {
            result = new MaterialLongBox();
        }
        if (tag.equals("materialmodal")) {
            result = new MaterialModal();
        }
        if (tag.equals("materialmodalcontent")) {
            result = new MaterialModalContent();
        }
        if (tag.equals("materialmodalfooter")) {
            result = new MaterialModalFooter();
        }
        if (tag.equals("materialnavbar")) {
            result = new MaterialNavBar();
        }
        if (tag.equals("materialnavbarshrink")) {
            result = new MaterialNavBarShrink();
        }
        if (tag.equals("materialnavbrand")) {
            result = new MaterialNavBrand();
        }
        if (tag.equals("materialnavcontent")) {
            result = new MaterialNavContent();
        }
        if (tag.equals("materialnavmenu")) {
            result = new MaterialNavMenu();
        }
        if (tag.equals("materialnavsection")) {
            result = new MaterialNavSection();
        }
        if (tag.equals("materialnoresult")) {
            result = new MaterialNoResult();
        }
        if (tag.equals("materialpager")) {
            result = new MaterialPager();
        }
        if (tag.equals("materialpanel")) {
            result = new MaterialPanel();
        }
        if (tag.equals("materialparallax")) {
            result = new MaterialParallax();
        }
        if (tag.equals("materialpreloader")) {
            result = new MaterialPreLoader();
        }
        if (tag.equals("materialprogress")) {
            result = new MaterialProgress();
        }
        if (tag.equals("materialrange")) {
            result = new MaterialRange();
        }
        if (tag.equals("materialrow")) {
            result = new MaterialRow();
        }
        if (tag.equals("materialscrollspy")) {
            result = new MaterialScrollspy();
        }
        if (tag.equals("materialsearch")) {
            result = new MaterialSearch();
        }
        if (tag.equals("materialsearchresult")) {
            result = new MaterialSearchResult();
        }
        if (tag.equals("materialsection")) {
            result = new MaterialSection();
        }
        if (tag.equals("materialsidenav")) {
            result = new MaterialSideNav();
        }
        if (tag.equals("materialsidenavcard")) {
            result = new MaterialSideNavCard();
        }
        if (tag.equals("materialsidenavcontent")) {
            result = new MaterialSideNavContent();
        }
        if (tag.equals("materialsidenavdrawer")) {
            result = new MaterialSideNavDrawer();
        }
        if (tag.equals("materialsidenavmini")) {
            result = new MaterialSideNavMini();
        }
        if (tag.equals("materialsidenavpush")) {
            result = new MaterialSideNavPush();
        }
        if (tag.equals("materialslidecaption")) {
            result = new MaterialSlideCaption();
        }
        if (tag.equals("materialslideitem")) {
            result = new MaterialSlideItem();
        }
        if (tag.equals("materialslider")) {
            result = new MaterialSlider();
        }
        if (tag.equals("materialspinner")) {
            result = new MaterialSpinner();
        }
        if (tag.equals("materialsplashscreen")) {
            result = new MaterialSplashScreen();
        }
        if (tag.equals("materialswitch")) {
            result = new MaterialSwitch();
        }
        if (tag.equals("materialtab")) {
            result = new MaterialTab();
        }
        if (tag.equals("materialtabitem")) {
            result = new MaterialTabItem();
        }
        if (tag.equals("materialtextarea")) {
            result = new MaterialTextArea();
        }
        if (tag.equals("materialtextbox")) {
            result = new MaterialTextBox();
        }
        if (tag.equals("materialtitle")) {
            result = new MaterialTitle();
        }
        if (tag.equals("materialtopnav")) {
            result = new MaterialTopNav();
        }
        if (tag.equals("materialvideo")) {
            result = new MaterialVideo();
        }
        if (tag.equals("materialweather")) {
            result = new MaterialWeather();
        }
        java.util.Optional<MaterialWidgetDefinition> def = this.getWidgetDefIfExist(tag);
        if ((def.isPresent() == true)&&(def.get().getExtendsMaterialWidget() == true)) {
            MaterialWidget candidate = ((MaterialWidget) result);
            if (candidate.getInitialClasses()!= null) {
                StringBuffer sb = new StringBuffer();
                for (String css: candidate.getInitialClasses()) {
                    sb.append(css).append(" ");
                }
                candidate.getElement().setAttribute("class", sb.toString().trim());
            }
        }
        if (result!= null) {
            return java.util.Optional.of(result);
        }
        return java.util.Optional.empty();
    }

    @Override
    public java.util.Optional<MaterialWidgetDefinition> getWidgetDefIfExist(String tag) {
        tag = tag.toLowerCase().replaceAll("-", "");
        if (widgets.containsKey(tag)) {
            MaterialWidgetDefinition field = widgets.get(tag);
            if (field.getExtendsMaterialWidget() == true) {
                field.getMethods().putAll(defaultMethods);
            }
            return java.util.Optional.of(field);
        }
        return java.util.Optional.empty();
    }

    @Override
    public java.util.Optional<MaterialMethodDefinition> getMethodDefIfExist(String tag, String method) {
        tag = tag.toLowerCase().replaceAll("-", "");
        method = method.toLowerCase();
        if (widgets.containsKey(tag) == false) {
            return java.util.Optional.empty();
        }
        if (widgets.get(tag).getMethods().containsKey(method)) {
            return java.util.Optional.of(widgets.get(tag).getMethods().get(method));
        }
        if (defaultMethods.containsKey(method)) {
            return java.util.Optional.of(defaultMethods.get(method.toLowerCase()));
        }
        return java.util.Optional.empty();
    }

    private void addMaterialWidget() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialWidget", MaterialWidget.class, false);
        field.getMethods().put("gwtdisplay", new MaterialMethodDefinition(com.google.gwt.dom.client.Style.Display.class, (this::setgwtdisplay_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("opacity", new MaterialMethodDefinition((double.class), (this::setopacity_gwt_material_design_client_base_hasopacity)));
        field.getMethods().put("flexaligncontent", new MaterialMethodDefinition(FlexAlignContent.class, (this::setflexaligncontent_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("paddingleft", new MaterialMethodDefinition((double.class), (this::setpaddingleft_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("tooltip", new MaterialMethodDefinition(String.class, (this::settooltip_gwt_material_design_client_base_hastooltip)));
        field.getMethods().put("overflow", new MaterialMethodDefinition(com.google.gwt.dom.client.Style.Overflow.class, (this::setoverflow_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("depth", new MaterialMethodDefinition((int.class), (this::setdepth_gwt_material_design_client_base_hasdepth)));
        field.getMethods().put("lineheight", new MaterialMethodDefinition((double.class), (this::setlineheight_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("visible", new MaterialMethodDefinition((boolean.class), (this::setvisible_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("hideon", new MaterialMethodDefinition(HideOn.class, (this::sethideon_gwt_material_design_client_base_hashideon)));
        field.getMethods().put("marginbottom", new MaterialMethodDefinition((double.class), (this::setmarginbottom_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("waves", new MaterialMethodDefinition(WavesType.class, (this::setwaves_gwt_material_design_client_base_haswaves)));
        field.getMethods().put("circle", new MaterialMethodDefinition((boolean.class), (this::setcircle_gwt_material_design_client_base_hascircle)));
        field.getMethods().put("top", new MaterialMethodDefinition((double.class), (this::settop_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("marginleft", new MaterialMethodDefinition((double.class), (this::setmarginleft_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("layoutposition", new MaterialMethodDefinition(com.google.gwt.dom.client.Style.Position.class, (this::setlayoutposition_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("fontweight", new MaterialMethodDefinition(com.google.gwt.dom.client.Style.FontWeight.class, (this::setfontweight_gwt_material_design_client_base_hasfontweight)));
        field.getMethods().put("flexgrow", new MaterialMethodDefinition(Integer.class, (this::setflexgrow_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("grid", new MaterialMethodDefinition(String.class, (this::setgrid_gwt_material_design_client_base_hasgrid)));
        field.getMethods().put("textalign", new MaterialMethodDefinition(TextAlign.class, (this::settextalign_gwt_material_design_client_base_hastextalign)));
        field.getMethods().put("hoverable", new MaterialMethodDefinition((boolean.class), (this::sethoverable_gwt_material_design_client_base_hashoverable)));
        field.getMethods().put("flex", new MaterialMethodDefinition(Flex.class, (this::setflex_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("textcolor", new MaterialMethodDefinition(Color.class, (this::settextcolor_gwt_material_design_client_base_hascolors)));
        field.getMethods().put("shadow", new MaterialMethodDefinition((int.class), (this::setshadow_gwt_material_design_client_base_hasshadow)));
        field.getMethods().put("tooltipposition", new MaterialMethodDefinition(gwt.material.design.client.constants.Position.class, (this::settooltipposition_gwt_material_design_client_base_hastooltip)));
        field.getMethods().put("visibility", new MaterialMethodDefinition(com.google.gwt.dom.client.Style.Visibility.class, (this::setvisibility_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("marginright", new MaterialMethodDefinition((double.class), (this::setmarginright_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("offset", new MaterialMethodDefinition(String.class, (this::setoffset_gwt_material_design_client_base_hasgrid)));
        BiConsumer<MaterialWidget, String> setheight_com_google_gwt_user_client_ui_uiobjectMaterialWidget = (this::setheight_com_google_gwt_user_client_ui_uiobjectMaterialWidget);
        field.getMethods().put("height", new MaterialMethodDefinition(String.class, setheight_com_google_gwt_user_client_ui_uiobjectMaterialWidget));
        field.getMethods().put("flexshrink", new MaterialMethodDefinition(Integer.class, (this::setflexshrink_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("flexorder", new MaterialMethodDefinition(Integer.class, (this::setflexorder_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("paddingbottom", new MaterialMethodDefinition((double.class), (this::setpaddingbottom_gwt_material_design_client_base_hasinlinestyle)));
        BiConsumer<MaterialWidget, String> settitle_com_google_gwt_user_client_ui_uiobjectMaterialWidget = (this::settitle_com_google_gwt_user_client_ui_uiobjectMaterialWidget);
        field.getMethods().put("title", new MaterialMethodDefinition(String.class, settitle_com_google_gwt_user_client_ui_uiobjectMaterialWidget));
        field.getMethods().put("padding", new MaterialMethodDefinition((double.class), (this::setpadding_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("right", new MaterialMethodDefinition((double.class), (this::setright_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("display", new MaterialMethodDefinition(gwt.material.design.client.constants.Display.class, (this::setdisplay_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("float", new MaterialMethodDefinition(com.google.gwt.dom.client.Style.Float.class, (this::setfloat_gwt_material_design_client_base_hasfloat)));
        field.getMethods().put("tooltipdelayms", new MaterialMethodDefinition((int.class), (this::settooltipdelayms_gwt_material_design_client_base_hastooltip)));
        field.getMethods().put("margin", new MaterialMethodDefinition((double.class), (this::setmargin_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("bottom", new MaterialMethodDefinition((double.class), (this::setbottom_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("showon", new MaterialMethodDefinition(ShowOn.class, (this::setshowon_gwt_material_design_client_base_hasshowon)));
        field.getMethods().put("flexdirection", new MaterialMethodDefinition(FlexDirection.class, (this::setflexdirection_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("tooltiphtml", new MaterialMethodDefinition(String.class, (this::settooltiphtml_gwt_material_design_client_base_hastooltip)));
        field.getMethods().put("truncate", new MaterialMethodDefinition((boolean.class), (this::settruncate_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("enabled", new MaterialMethodDefinition((boolean.class), (this::setenabled_com_google_gwt_user_client_ui_hasenabled)));
        field.getMethods().put("margintop", new MaterialMethodDefinition((double.class), (this::setmargintop_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("display", new MaterialMethodDefinition(gwt.material.design.client.constants.Display.class, (this::setdisplay_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("style", new MaterialMethodDefinition(String.class, (this::setstyle_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("paddingtop", new MaterialMethodDefinition((double.class), (this::setpaddingtop_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("paddingright", new MaterialMethodDefinition((double.class), (this::setpaddingright_gwt_material_design_client_base_hasinlinestyle)));
        field.getMethods().put("tabindex", new MaterialMethodDefinition((int.class), (this::settabindex_com_google_gwt_user_client_ui_focusable)));
        field.getMethods().put("separator", new MaterialMethodDefinition((boolean.class), (this::setseparator_gwt_material_design_client_base_hasseparator)));
        field.getMethods().put("flexbasis", new MaterialMethodDefinition(String.class, (this::setflexbasis_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("flexwrap", new MaterialMethodDefinition(FlexWrap.class, (this::setflexwrap_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("fontsize", new MaterialMethodDefinition(String.class, (this::setfontsize_gwt_material_design_client_base_hasfontsize)));
        field.getMethods().put("centeron", new MaterialMethodDefinition(CenterOn.class, (this::setcenteron_gwt_material_design_client_base_hascenteron)));
        field.getMethods().put("visible", new MaterialMethodDefinition((boolean.class), (this::setvisible_com_google_gwt_user_client_ui_hasvisibility)));
        field.getMethods().put("focus", new MaterialMethodDefinition((boolean.class), (this::setfocus_com_google_gwt_user_client_ui_focusable)));
        BiConsumer<MaterialWidget, String> setwidth_com_google_gwt_user_client_ui_uiobjectMaterialWidget = (this::setwidth_com_google_gwt_user_client_ui_uiobjectMaterialWidget);
        field.getMethods().put("width", new MaterialMethodDefinition(String.class, setwidth_com_google_gwt_user_client_ui_uiobjectMaterialWidget));
        field.getMethods().put("flexalignself", new MaterialMethodDefinition(FlexAlignSelf.class, (this::setflexalignself_gwt_material_design_client_base_hasflexbox)));
        field.getMethods().put("left", new MaterialMethodDefinition((double.class), (this::setleft_gwt_material_design_client_base_materialwidget)));
        field.getMethods().put("backgroundcolor", new MaterialMethodDefinition(Color.class, (this::setbackgroundcolor_gwt_material_design_client_base_hascolors)));
        this.defaultMethods.putAll(field.getMethods());
        this.widgets.put("materialwidget", field);
    }

    private void addMaterialAnchorButton() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialAnchorButton", MaterialAnchorButton.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        BiConsumer<MaterialWidget, ButtonSize> setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("size", new MaterialMethodDefinition(ButtonSize.class, setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        BiConsumer<MaterialWidget, String> settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        BiConsumer<MaterialWidget, ButtonType> settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("type", new MaterialMethodDefinition(ButtonType.class, settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialanchorbutton", field);
    }

    private void addMaterialBadge() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialBadge", MaterialBadge.class, true);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        this.widgets.put("materialbadge", field);
    }

    private void addMaterialBreadcrumb() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialBreadcrumb", MaterialBreadcrumb.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        BiConsumer<MaterialWidget, ButtonSize> setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("size", new MaterialMethodDefinition(ButtonSize.class, setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        BiConsumer<MaterialWidget, String> settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        BiConsumer<MaterialWidget, ButtonType> settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("type", new MaterialMethodDefinition(ButtonType.class, settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialbreadcrumb", field);
    }

    private void addMaterialButton() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialButton", MaterialButton.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        BiConsumer<MaterialWidget, ButtonSize> setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("size", new MaterialMethodDefinition(ButtonSize.class, setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        BiConsumer<MaterialWidget, String> settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        BiConsumer<MaterialWidget, ButtonType> settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("type", new MaterialMethodDefinition(ButtonType.class, settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialbutton", field);
    }

    private void addMaterialCard() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCard", MaterialCard.class, true);
        field.getMethods().put("detectorientation", new MaterialMethodDefinition((boolean.class), (this::setdetectorientation_gwt_material_design_client_ui_materialcard)));
        field.getMethods().put("axis", new MaterialMethodDefinition(Axis.class, (this::setaxis_gwt_material_design_client_base_hasaxis)));
        this.widgets.put("materialcard", field);
    }

    private void addMaterialCardAction() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCardAction", MaterialCardAction.class, true);
        this.widgets.put("materialcardaction", field);
    }

    private void addMaterialCardContent() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCardContent", MaterialCardContent.class, true);
        this.widgets.put("materialcardcontent", field);
    }

    private void addMaterialCardImage() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCardImage", MaterialCardImage.class, true);
        this.widgets.put("materialcardimage", field);
    }

    private void addMaterialCardReveal() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCardReveal", MaterialCardReveal.class, true);
        this.widgets.put("materialcardreveal", field);
    }

    private void addMaterialCardTitle() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCardTitle", MaterialCardTitle.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialcardtitle", field);
    }

    private void addMaterialChip() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialChip", MaterialChip.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("url", new MaterialMethodDefinition(String.class, (this::seturl_gwt_material_design_client_base_hasimage)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("letterbackgroundcolor", new MaterialMethodDefinition(Color.class, (this::setletterbackgroundcolor_gwt_material_design_client_base_hasletter)));
        field.getMethods().put("lettercolor", new MaterialMethodDefinition(Color.class, (this::setlettercolor_gwt_material_design_client_base_hasletter)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_gwt_material_design_client_ui_materialchip)));
        field.getMethods().put("letter", new MaterialMethodDefinition(String.class, (this::setletter_gwt_material_design_client_base_hasletter)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialchip", field);
    }

    private void addMaterialCollapsible() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCollapsible", MaterialCollapsible.class, true);
        field.getMethods().put("accordion", new MaterialMethodDefinition((boolean.class), (this::setaccordion_gwt_material_design_client_ui_materialcollapsible)));
        field.getMethods().put("type", new MaterialMethodDefinition(CollapsibleType.class, (this::settype_gwt_material_design_client_ui_materialcollapsible)));
        field.getMethods().put("active", new MaterialMethodDefinition((int.class), (this::setactive_gwt_material_design_client_base_hasactiveparent)));
        this.widgets.put("materialcollapsible", field);
    }

    private void addMaterialCollapsibleBody() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCollapsibleBody", MaterialCollapsibleBody.class, true);
        this.widgets.put("materialcollapsiblebody", field);
    }

    private void addMaterialCollapsibleHeader() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCollapsibleHeader", MaterialCollapsibleHeader.class, true);
        this.widgets.put("materialcollapsibleheader", field);
    }

    private void addMaterialCollapsibleItem() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCollapsibleItem", MaterialCollapsibleItem.class, true);
        BiConsumer<MaterialWidget, ButtonSize> setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("size", new MaterialMethodDefinition(ButtonSize.class, setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        BiConsumer<MaterialWidget, String> settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        BiConsumer<MaterialWidget, ButtonType> settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("type", new MaterialMethodDefinition(ButtonType.class, settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("percent", new MaterialMethodDefinition((double.class), (this::setpercent_gwt_material_design_client_base_hasprogress)));
        this.widgets.put("materialcollapsibleitem", field);
    }

    private void addMaterialCollection() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCollection", MaterialCollection.class, true);
        field.getMethods().put("header", new MaterialMethodDefinition(String.class, (this::setheader_gwt_material_design_client_ui_materialcollection)));
        field.getMethods().put("active", new MaterialMethodDefinition((int.class), (this::setactive_gwt_material_design_client_base_hasactiveparent)));
        this.widgets.put("materialcollection", field);
    }

    private void addMaterialCollectionItem() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCollectionItem", MaterialCollectionItem.class, true);
        field.getMethods().put("avatar", new MaterialMethodDefinition((boolean.class), (this::setavatar_gwt_material_design_client_base_hasavatar)));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("dismissible", new MaterialMethodDefinition((boolean.class), (this::setdismissible_gwt_material_design_client_base_hasdismissible)));
        field.getMethods().put("type", new MaterialMethodDefinition(CollectionType.class, (this::settype_gwt_material_design_client_ui_materialcollectionitem)));
        this.widgets.put("materialcollectionitem", field);
    }

    private void addMaterialCollectionSecondary() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialCollectionSecondary", MaterialCollectionSecondary.class, true);
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        this.widgets.put("materialcollectionsecondary", field);
    }

    private void addMaterialColumn() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialColumn", MaterialColumn.class, true);
        this.widgets.put("materialcolumn", field);
    }

    private void addMaterialContainer() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialContainer", MaterialContainer.class, true);
        this.widgets.put("materialcontainer", field);
    }

    private void addMaterialDatePicker() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialDatePicker", MaterialDatePicker.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("yearstodisplay", new MaterialMethodDefinition((int.class), (this::setyearstodisplay_gwt_material_design_client_ui_materialdatepicker)));
        field.getMethods().put("language", new MaterialMethodDefinition(DatePickerLanguage.class, (this::setlanguage_gwt_material_design_client_ui_materialdatepicker)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("orientation", new MaterialMethodDefinition(Orientation.class, (this::setorientation_gwt_material_design_client_base_hasorientation)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        field.getMethods().put("detectorientation", new MaterialMethodDefinition((boolean.class), (this::setdetectorientation_gwt_material_design_client_ui_materialdatepicker)));
        field.getMethods().put("selectiontype", new MaterialMethodDefinition(MaterialDatePicker.MaterialDatePickerType.class, (this::setselectiontype_gwt_material_design_client_ui_materialdatepicker)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("dateselectiontype", new MaterialMethodDefinition(MaterialDatePicker.MaterialDatePickerType.class, (this::setdateselectiontype_gwt_material_design_client_ui_materialdatepicker)));
        field.getMethods().put("container", new MaterialMethodDefinition(DatePickerContainer.class, (this::setcontainer_gwt_material_design_client_ui_materialdatepicker)));
        field.getMethods().put("autoclose", new MaterialMethodDefinition((boolean.class), (this::setautoclose_gwt_material_design_client_ui_materialdatepicker)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("format", new MaterialMethodDefinition(String.class, (this::setformat_gwt_material_design_client_ui_materialdatepicker)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialdatepicker", field);
    }

    private void addMaterialDivider() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialDivider", MaterialDivider.class, true);
        this.widgets.put("materialdivider", field);
    }

    private void addMaterialDoubleBox() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialDoubleBox", MaterialDoubleBox.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("directionestimator", new MaterialMethodDefinition((boolean.class), (this::setdirectionestimator_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_com_google_gwt_user_client_ui_hasname)));
        field.getMethods().put("cursorpos", new MaterialMethodDefinition((int.class), (this::setcursorpos_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        BiConsumer<MaterialWidget, String> setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("max", new MaterialMethodDefinition(String.class, setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        field.getMethods().put("direction", new MaterialMethodDefinition(com.google.gwt.i18n.client.HasDirection.Direction.class, (this::setdirection_gwt_material_design_client_ui_materialvaluebox)));
        BiConsumer<MaterialWidget, String> setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("step", new MaterialMethodDefinition(String.class, setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        BiConsumer<MaterialWidget, String> setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("min", new MaterialMethodDefinition(String.class, setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        field.getMethods().put("length", new MaterialMethodDefinition((int.class), (this::setlength_gwt_material_design_client_base_hascounter)));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("type", new MaterialMethodDefinition(InputType.class, (this::settype_gwt_material_design_client_base_hasinputtype)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("alignment", new MaterialMethodDefinition(com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment.class, (this::setalignment_gwt_material_design_client_ui_materialvaluebox)));
        this.widgets.put("materialdoublebox", field);
    }

    private void addMaterialDropDown() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialDropDown", MaterialDropDown.class, true);
        field.getMethods().put("alignment", new MaterialMethodDefinition(Alignment.class, (this::setalignment_gwt_material_design_client_ui_materialdropdown)));
        field.getMethods().put("induration", new MaterialMethodDefinition((int.class), (this::setinduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("outduration", new MaterialMethodDefinition((int.class), (this::setoutduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("activator", new MaterialMethodDefinition(String.class, (this::setactivator_gwt_material_design_client_ui_materialdropdown)));
        field.getMethods().put("gutter", new MaterialMethodDefinition((int.class), (this::setgutter_gwt_material_design_client_ui_materialdropdown)));
        field.getMethods().put("constrainwidth", new MaterialMethodDefinition((boolean.class), (this::setconstrainwidth_gwt_material_design_client_ui_materialdropdown)));
        field.getMethods().put("beloworigin", new MaterialMethodDefinition((boolean.class), (this::setbeloworigin_gwt_material_design_client_ui_materialdropdown)));
        field.getMethods().put("hover", new MaterialMethodDefinition((boolean.class), (this::sethover_gwt_material_design_client_ui_materialdropdown)));
        this.widgets.put("materialdropdown", field);
    }

    private void addMaterialFAB() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialFAB", MaterialFAB.class, true);
        field.getMethods().put("axis", new MaterialMethodDefinition(Axis.class, (this::setaxis_gwt_material_design_client_base_hasaxis)));
        field.getMethods().put("type", new MaterialMethodDefinition(FABType.class, (this::settype_gwt_material_design_client_ui_materialfab)));
        this.widgets.put("materialfab", field);
    }

    private void addMaterialFABList() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialFABList", MaterialFABList.class, true);
        this.widgets.put("materialfablist", field);
    }

    private void addMaterialFloatBox() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialFloatBox", MaterialFloatBox.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("directionestimator", new MaterialMethodDefinition((boolean.class), (this::setdirectionestimator_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_com_google_gwt_user_client_ui_hasname)));
        field.getMethods().put("cursorpos", new MaterialMethodDefinition((int.class), (this::setcursorpos_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        BiConsumer<MaterialWidget, String> setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("max", new MaterialMethodDefinition(String.class, setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        field.getMethods().put("direction", new MaterialMethodDefinition(com.google.gwt.i18n.client.HasDirection.Direction.class, (this::setdirection_gwt_material_design_client_ui_materialvaluebox)));
        BiConsumer<MaterialWidget, String> setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("step", new MaterialMethodDefinition(String.class, setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        BiConsumer<MaterialWidget, String> setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("min", new MaterialMethodDefinition(String.class, setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        field.getMethods().put("length", new MaterialMethodDefinition((int.class), (this::setlength_gwt_material_design_client_base_hascounter)));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("type", new MaterialMethodDefinition(InputType.class, (this::settype_gwt_material_design_client_base_hasinputtype)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("alignment", new MaterialMethodDefinition(com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment.class, (this::setalignment_gwt_material_design_client_ui_materialvaluebox)));
        this.widgets.put("materialfloatbox", field);
    }

    private void addMaterialFooter() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialFooter", MaterialFooter.class, true);
        field.getMethods().put("type", new MaterialMethodDefinition(FooterType.class, (this::settype_gwt_material_design_client_ui_materialfooter)));
        this.widgets.put("materialfooter", field);
    }

    private void addMaterialFooterCopyright() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialFooterCopyright", MaterialFooterCopyright.class, true);
        this.widgets.put("materialfootercopyright", field);
    }

    private void addMaterialHeader() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialHeader", MaterialHeader.class, true);
        this.widgets.put("materialheader", field);
    }

    private void addMaterialHelpBlock() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialHelpBlock", MaterialHelpBlock.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialhelpblock", field);
    }

    private void addMaterialIcon() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialIcon", MaterialIcon.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        BiConsumer<MaterialWidget, ButtonSize> setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("size", new MaterialMethodDefinition(ButtonSize.class, setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        BiConsumer<MaterialWidget, String> settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        BiConsumer<MaterialWidget, ButtonType> settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("type", new MaterialMethodDefinition(ButtonType.class, settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("innertext", new MaterialMethodDefinition(String.class, (this::setinnertext_gwt_material_design_client_ui_materialicon)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialicon", field);
    }

    private void addMaterialImage() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialImage", MaterialImage.class, true);
        field.getMethods().put("caption", new MaterialMethodDefinition(String.class, (this::setcaption_gwt_material_design_client_base_hascaption)));
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        field.getMethods().put("type", new MaterialMethodDefinition(ImageType.class, (this::settype_gwt_material_design_client_ui_materialimage)));
        field.getMethods().put("url", new MaterialMethodDefinition(String.class, (this::seturl_gwt_material_design_client_base_hasimage)));
        this.widgets.put("materialimage", field);
    }

    private void addMaterialInput() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialInput", MaterialInput.class, true);
        field.getMethods().put("max", new MaterialMethodDefinition(String.class, (this::setmax_gwt_material_design_client_ui_materialinput)));
        field.getMethods().put("min", new MaterialMethodDefinition(String.class, (this::setmin_gwt_material_design_client_ui_materialinput)));
        field.getMethods().put("required", new MaterialMethodDefinition((boolean.class), (this::setrequired_gwt_material_design_client_ui_materialinput)));
        field.getMethods().put("type", new MaterialMethodDefinition(InputType.class, (this::settype_gwt_material_design_client_base_hasinputtype)));
        this.widgets.put("materialinput", field);
    }

    private void addMaterialIntegerBox() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialIntegerBox", MaterialIntegerBox.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("directionestimator", new MaterialMethodDefinition((boolean.class), (this::setdirectionestimator_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_com_google_gwt_user_client_ui_hasname)));
        field.getMethods().put("cursorpos", new MaterialMethodDefinition((int.class), (this::setcursorpos_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        BiConsumer<MaterialWidget, String> setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("max", new MaterialMethodDefinition(String.class, setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        field.getMethods().put("direction", new MaterialMethodDefinition(com.google.gwt.i18n.client.HasDirection.Direction.class, (this::setdirection_gwt_material_design_client_ui_materialvaluebox)));
        BiConsumer<MaterialWidget, String> setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("step", new MaterialMethodDefinition(String.class, setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        BiConsumer<MaterialWidget, String> setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("min", new MaterialMethodDefinition(String.class, setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        field.getMethods().put("length", new MaterialMethodDefinition((int.class), (this::setlength_gwt_material_design_client_base_hascounter)));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("type", new MaterialMethodDefinition(InputType.class, (this::settype_gwt_material_design_client_base_hasinputtype)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("alignment", new MaterialMethodDefinition(com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment.class, (this::setalignment_gwt_material_design_client_ui_materialvaluebox)));
        this.widgets.put("materialintegerbox", field);
    }

    private void addMaterialLabel() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialLabel", MaterialLabel.class, true);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        this.widgets.put("materiallabel", field);
    }

    private void addMaterialLink() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialLink", MaterialLink.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        BiConsumer<MaterialWidget, ButtonSize> setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("size", new MaterialMethodDefinition(ButtonSize.class, setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        BiConsumer<MaterialWidget, String> settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        BiConsumer<MaterialWidget, ButtonType> settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("type", new MaterialMethodDefinition(ButtonType.class, settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materiallink", field);
    }

    private void addMaterialListBox() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialListBox", MaterialListBox.class, true);
        field.getMethods().put("multipleselect", new MaterialMethodDefinition((boolean.class), (this::setmultipleselect_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        field.getMethods().put("title", new MaterialMethodDefinition(String.class, (this::settitle_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("emptyplaceholder", new MaterialMethodDefinition(String.class, (this::setemptyplaceholder_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("selectedvalue", new MaterialMethodDefinition(String.class, (this::setselectedvalue_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("visibleitemcount", new MaterialMethodDefinition((int.class), (this::setvisibleitemcount_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("selectedindex", new MaterialMethodDefinition((int.class), (this::setselectedindex_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("old", new MaterialMethodDefinition((boolean.class), (this::setold_gwt_material_design_client_ui_materiallistvaluebox)));
        this.widgets.put("materiallistbox", field);
    }

    private void addMaterialListValueBox() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialListValueBox", MaterialListValueBox.class, true);
        field.getMethods().put("multipleselect", new MaterialMethodDefinition((boolean.class), (this::setmultipleselect_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        field.getMethods().put("title", new MaterialMethodDefinition(String.class, (this::settitle_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("emptyplaceholder", new MaterialMethodDefinition(String.class, (this::setemptyplaceholder_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("selectedvalue", new MaterialMethodDefinition(String.class, (this::setselectedvalue_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("visibleitemcount", new MaterialMethodDefinition((int.class), (this::setvisibleitemcount_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("selectedindex", new MaterialMethodDefinition((int.class), (this::setselectedindex_gwt_material_design_client_ui_materiallistvaluebox)));
        field.getMethods().put("old", new MaterialMethodDefinition((boolean.class), (this::setold_gwt_material_design_client_ui_materiallistvaluebox)));
        this.widgets.put("materiallistvaluebox", field);
    }

    private void addMaterialLongBox() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialLongBox", MaterialLongBox.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("directionestimator", new MaterialMethodDefinition((boolean.class), (this::setdirectionestimator_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_com_google_gwt_user_client_ui_hasname)));
        field.getMethods().put("cursorpos", new MaterialMethodDefinition((int.class), (this::setcursorpos_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        BiConsumer<MaterialWidget, String> setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("max", new MaterialMethodDefinition(String.class, setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        field.getMethods().put("direction", new MaterialMethodDefinition(com.google.gwt.i18n.client.HasDirection.Direction.class, (this::setdirection_gwt_material_design_client_ui_materialvaluebox)));
        BiConsumer<MaterialWidget, String> setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("step", new MaterialMethodDefinition(String.class, setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        BiConsumer<MaterialWidget, String> setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox = (this::setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox);
        field.getMethods().put("min", new MaterialMethodDefinition(String.class, setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox));
        field.getMethods().put("length", new MaterialMethodDefinition((int.class), (this::setlength_gwt_material_design_client_base_hascounter)));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("type", new MaterialMethodDefinition(InputType.class, (this::settype_gwt_material_design_client_base_hasinputtype)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("alignment", new MaterialMethodDefinition(com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment.class, (this::setalignment_gwt_material_design_client_ui_materialvaluebox)));
        this.widgets.put("materiallongbox", field);
    }

    private void addMaterialModal() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialModal", MaterialModal.class, true);
        field.getMethods().put("induration", new MaterialMethodDefinition((int.class), (this::setinduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("outduration", new MaterialMethodDefinition((int.class), (this::setoutduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("type", new MaterialMethodDefinition(ModalType.class, (this::settype_gwt_material_design_client_ui_materialmodal)));
        field.getMethods().put("dismissible", new MaterialMethodDefinition((boolean.class), (this::setdismissible_gwt_material_design_client_base_hasdismissible)));
        this.widgets.put("materialmodal", field);
    }

    private void addMaterialModalContent() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialModalContent", MaterialModalContent.class, true);
        this.widgets.put("materialmodalcontent", field);
    }

    private void addMaterialModalFooter() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialModalFooter", MaterialModalFooter.class, true);
        this.widgets.put("materialmodalfooter", field);
    }

    private void addMaterialNavBar() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialNavBar", MaterialNavBar.class, true);
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        field.getMethods().put("percent", new MaterialMethodDefinition((double.class), (this::setpercent_gwt_material_design_client_base_hasprogress)));
        this.widgets.put("materialnavbar", field);
    }

    private void addMaterialNavBarShrink() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialNavBarShrink", MaterialNavBarShrink.class, true);
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        field.getMethods().put("percent", new MaterialMethodDefinition((double.class), (this::setpercent_gwt_material_design_client_base_hasprogress)));
        this.widgets.put("materialnavbarshrink", field);
    }

    private void addMaterialNavBrand() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialNavBrand", MaterialNavBrand.class, true);
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        field.getMethods().put("position", new MaterialMethodDefinition(gwt.material.design.client.constants.Position.class, (this::setposition_gwt_material_design_client_base_hasposition)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_gwt_material_design_client_ui_materialnavbrand)));
        this.widgets.put("materialnavbrand", field);
    }

    private void addMaterialNavContent() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialNavContent", MaterialNavContent.class, true);
        this.widgets.put("materialnavcontent", field);
    }

    private void addMaterialNavMenu() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialNavMenu", MaterialNavMenu.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        BiConsumer<MaterialWidget, ButtonSize> setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("size", new MaterialMethodDefinition(ButtonSize.class, setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("target", new MaterialMethodDefinition(String.class, (this::settarget_gwt_material_design_client_base_hashref)));
        field.getMethods().put("href", new MaterialMethodDefinition(String.class, (this::sethref_gwt_material_design_client_base_hashref)));
        field.getMethods().put("activates", new MaterialMethodDefinition(String.class, (this::setactivates_gwt_material_design_client_base_hasactivates)));
        BiConsumer<MaterialWidget, String> settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        BiConsumer<MaterialWidget, ButtonType> settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton = (this::settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton);
        field.getMethods().put("type", new MaterialMethodDefinition(ButtonType.class, settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialnavmenu", field);
    }

    private void addMaterialNavSection() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialNavSection", MaterialNavSection.class, true);
        field.getMethods().put("position", new MaterialMethodDefinition(gwt.material.design.client.constants.Position.class, (this::setposition_gwt_material_design_client_base_hasposition)));
        this.widgets.put("materialnavsection", field);
    }

    private void addMaterialNoResult() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialNoResult", MaterialNoResult.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("description", new MaterialMethodDefinition(String.class, (this::setdescription_gwt_material_design_client_base_hastitle)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("title", new MaterialMethodDefinition(String.class, (this::settitle_gwt_material_design_client_base_hastitle)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        this.widgets.put("materialnoresult", field);
    }

    private void addMaterialPager() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialPager", MaterialPager.class, true);
        field.getMethods().put("total", new MaterialMethodDefinition((int.class), (this::settotal_gwt_material_design_client_ui_materialpager)));
        field.getMethods().put("maxpagelinksshown", new MaterialMethodDefinition((int.class), (this::setmaxpagelinksshown_gwt_material_design_client_ui_materialpager)));
        field.getMethods().put("pagesize", new MaterialMethodDefinition((int.class), (this::setpagesize_gwt_material_design_client_ui_materialpager)));
        field.getMethods().put("currentpage", new MaterialMethodDefinition((int.class), (this::setcurrentpage_gwt_material_design_client_ui_materialpager)));
        field.getMethods().put("indicatortemplate", new MaterialMethodDefinition(String.class, (this::setindicatortemplate_gwt_material_design_client_ui_materialpager)));
        field.getMethods().put("enableindicator", new MaterialMethodDefinition((boolean.class), (this::setenableindicator_gwt_material_design_client_ui_materialpager)));
        this.widgets.put("materialpager", field);
    }

    private void addMaterialPanel() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialPanel", MaterialPanel.class, true);
        this.widgets.put("materialpanel", field);
    }

    private void addMaterialParallax() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialParallax", MaterialParallax.class, true);
        this.widgets.put("materialparallax", field);
    }

    private void addMaterialPreLoader() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialPreLoader", MaterialPreLoader.class, true);
        field.getMethods().put("size", new MaterialMethodDefinition(LoaderSize.class, (this::setsize_gwt_material_design_client_ui_materialpreloader)));
        this.widgets.put("materialpreloader", field);
    }

    private void addMaterialProgress() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialProgress", MaterialProgress.class, true);
        field.getMethods().put("percent", new MaterialMethodDefinition((double.class), (this::setpercent_gwt_material_design_client_ui_materialprogress)));
        field.getMethods().put("type", new MaterialMethodDefinition(ProgressType.class, (this::settype_gwt_material_design_client_ui_materialprogress)));
        field.getMethods().put("color", new MaterialMethodDefinition(Color.class, (this::setcolor_gwt_material_design_client_ui_materialprogress)));
        this.widgets.put("materialprogress", field);
    }

    private void addMaterialRange() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialRange", MaterialRange.class, true);
        field.getMethods().put("max", new MaterialMethodDefinition(Integer.class, (this::setmax_gwt_material_design_client_ui_materialrange)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        field.getMethods().put("min", new MaterialMethodDefinition(Integer.class, (this::setmin_gwt_material_design_client_ui_materialrange)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        this.widgets.put("materialrange", field);
    }

    private void addMaterialRow() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialRow", MaterialRow.class, true);
        this.widgets.put("materialrow", field);
    }

    private void addMaterialScrollspy() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialScrollspy", MaterialScrollspy.class, true);
        this.widgets.put("materialscrollspy", field);
    }

    private void addMaterialSearch() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSearch", MaterialSearch.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("directionestimator", new MaterialMethodDefinition((boolean.class), (this::setdirectionestimator_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_com_google_gwt_user_client_ui_hasname)));
        field.getMethods().put("cursorpos", new MaterialMethodDefinition((int.class), (this::setcursorpos_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        field.getMethods().put("direction", new MaterialMethodDefinition(com.google.gwt.i18n.client.HasDirection.Direction.class, (this::setdirection_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("length", new MaterialMethodDefinition((int.class), (this::setlength_gwt_material_design_client_base_hascounter)));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("cursel", new MaterialMethodDefinition((int.class), (this::setcursel_gwt_material_design_client_ui_materialsearch)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("type", new MaterialMethodDefinition(InputType.class, (this::settype_gwt_material_design_client_base_hasinputtype)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("alignment", new MaterialMethodDefinition(com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment.class, (this::setalignment_gwt_material_design_client_ui_materialvaluebox)));
        this.widgets.put("materialsearch", field);
    }

    private void addMaterialSearchResult() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSearchResult", MaterialSearchResult.class, true);
        this.widgets.put("materialsearchresult", field);
    }

    private void addMaterialSection() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSection", MaterialSection.class, true);
        this.widgets.put("materialsection", field);
    }

    private void addMaterialSideNav() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSideNav", MaterialSideNav.class, true);
        field.getMethods().put("edge", new MaterialMethodDefinition(Edge.class, (this::setedge_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("width", new MaterialMethodDefinition(String.class, (this::setwidth_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("induration", new MaterialMethodDefinition((int.class), (this::setinduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("outduration", new MaterialMethodDefinition((int.class), (this::setoutduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("allowbodyscroll", new MaterialMethodDefinition((boolean.class), (this::setallowbodyscroll_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("alwaysshowactivator", new MaterialMethodDefinition((boolean.class), (this::setalwaysshowactivator_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("closeonclick", new MaterialMethodDefinition((boolean.class), (this::setcloseonclick_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("showonattach", new MaterialMethodDefinition((boolean.class), (this::setshowonattach_gwt_material_design_client_ui_materialsidenav)));
        this.widgets.put("materialsidenav", field);
    }

    private void addMaterialSideNavCard() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSideNavCard", MaterialSideNavCard.class, true);
        field.getMethods().put("edge", new MaterialMethodDefinition(Edge.class, (this::setedge_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("width", new MaterialMethodDefinition(String.class, (this::setwidth_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("induration", new MaterialMethodDefinition((int.class), (this::setinduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("outduration", new MaterialMethodDefinition((int.class), (this::setoutduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("allowbodyscroll", new MaterialMethodDefinition((boolean.class), (this::setallowbodyscroll_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("alwaysshowactivator", new MaterialMethodDefinition((boolean.class), (this::setalwaysshowactivator_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("closeonclick", new MaterialMethodDefinition((boolean.class), (this::setcloseonclick_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("showonattach", new MaterialMethodDefinition((boolean.class), (this::setshowonattach_gwt_material_design_client_ui_materialsidenav)));
        this.widgets.put("materialsidenavcard", field);
    }

    private void addMaterialSideNavContent() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSideNavContent", MaterialSideNavContent.class, true);
        this.widgets.put("materialsidenavcontent", field);
    }

    private void addMaterialSideNavDrawer() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSideNavDrawer", MaterialSideNavDrawer.class, true);
        field.getMethods().put("edge", new MaterialMethodDefinition(Edge.class, (this::setedge_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("width", new MaterialMethodDefinition(String.class, (this::setwidth_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("induration", new MaterialMethodDefinition((int.class), (this::setinduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("outduration", new MaterialMethodDefinition((int.class), (this::setoutduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("allowbodyscroll", new MaterialMethodDefinition((boolean.class), (this::setallowbodyscroll_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("alwaysshowactivator", new MaterialMethodDefinition((boolean.class), (this::setalwaysshowactivator_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("closeonclick", new MaterialMethodDefinition((boolean.class), (this::setcloseonclick_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("showonattach", new MaterialMethodDefinition((boolean.class), (this::setshowonattach_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("withheader", new MaterialMethodDefinition((boolean.class), (this::setwithheader_gwt_material_design_client_base_haswithheader)));
        this.widgets.put("materialsidenavdrawer", field);
    }

    private void addMaterialSideNavMini() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSideNavMini", MaterialSideNavMini.class, true);
        field.getMethods().put("edge", new MaterialMethodDefinition(Edge.class, (this::setedge_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("width", new MaterialMethodDefinition(String.class, (this::setwidth_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("induration", new MaterialMethodDefinition((int.class), (this::setinduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("outduration", new MaterialMethodDefinition((int.class), (this::setoutduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("expandable", new MaterialMethodDefinition((boolean.class), (this::setexpandable_gwt_material_design_client_ui_materialsidenavmini)));
        field.getMethods().put("allowbodyscroll", new MaterialMethodDefinition((boolean.class), (this::setallowbodyscroll_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("alwaysshowactivator", new MaterialMethodDefinition((boolean.class), (this::setalwaysshowactivator_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("closeonclick", new MaterialMethodDefinition((boolean.class), (this::setcloseonclick_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("showonattach", new MaterialMethodDefinition((boolean.class), (this::setshowonattach_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("expandonclick", new MaterialMethodDefinition((boolean.class), (this::setexpandonclick_gwt_material_design_client_ui_materialsidenavmini)));
        this.widgets.put("materialsidenavmini", field);
    }

    private void addMaterialSideNavPush() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSideNavPush", MaterialSideNavPush.class, true);
        field.getMethods().put("edge", new MaterialMethodDefinition(Edge.class, (this::setedge_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("width", new MaterialMethodDefinition(String.class, (this::setwidth_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("induration", new MaterialMethodDefinition((int.class), (this::setinduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("outduration", new MaterialMethodDefinition((int.class), (this::setoutduration_gwt_material_design_client_base_hasinoutdurationtransition)));
        field.getMethods().put("allowbodyscroll", new MaterialMethodDefinition((boolean.class), (this::setallowbodyscroll_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("alwaysshowactivator", new MaterialMethodDefinition((boolean.class), (this::setalwaysshowactivator_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("closeonclick", new MaterialMethodDefinition((boolean.class), (this::setcloseonclick_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("showonattach", new MaterialMethodDefinition((boolean.class), (this::setshowonattach_gwt_material_design_client_ui_materialsidenav)));
        field.getMethods().put("withheader", new MaterialMethodDefinition((boolean.class), (this::setwithheader_gwt_material_design_client_base_haswithheader)));
        this.widgets.put("materialsidenavpush", field);
    }

    private void addMaterialSlideCaption() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSlideCaption", MaterialSlideCaption.class, true);
        this.widgets.put("materialslidecaption", field);
    }

    private void addMaterialSlideItem() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSlideItem", MaterialSlideItem.class, true);
        this.widgets.put("materialslideitem", field);
    }

    private void addMaterialSlider() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSlider", MaterialSlider.class, true);
        field.getMethods().put("fullwidth", new MaterialMethodDefinition((boolean.class), (this::setfullwidth_gwt_material_design_client_ui_materialslider)));
        field.getMethods().put("interval", new MaterialMethodDefinition((int.class), (this::setinterval_gwt_material_design_client_ui_materialslider)));
        field.getMethods().put("duration", new MaterialMethodDefinition((int.class), (this::setduration_gwt_material_design_client_base_hasdurationtransition)));
        field.getMethods().put("height", new MaterialMethodDefinition(String.class, (this::setheight_gwt_material_design_client_ui_materialslider)));
        this.widgets.put("materialslider", field);
    }

    private void addMaterialSpinner() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSpinner", MaterialSpinner.class, true);
        field.getMethods().put("color", new MaterialMethodDefinition(SpinnerColor.class, (this::setcolor_gwt_material_design_client_ui_materialspinner)));
        this.widgets.put("materialspinner", field);
    }

    private void addMaterialSplashScreen() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSplashScreen", MaterialSplashScreen.class, true);
        this.widgets.put("materialsplashscreen", field);
    }

    private void addMaterialSwitch() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialSwitch", MaterialSwitch.class, true);
        field.getMethods().put("value", new MaterialMethodDefinition(Boolean.class, (this::setvalue_gwt_material_design_client_ui_materialswitch)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        field.getMethods().put("onlabel", new MaterialMethodDefinition(String.class, (this::setonlabel_gwt_material_design_client_ui_materialswitch)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("offlabel", new MaterialMethodDefinition(String.class, (this::setofflabel_gwt_material_design_client_ui_materialswitch)));
        this.widgets.put("materialswitch", field);
    }

    private void addMaterialTab() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialTab", MaterialTab.class, true);
        field.getMethods().put("type", new MaterialMethodDefinition(TabType.class, (this::settype_gwt_material_design_client_ui_materialtab)));
        field.getMethods().put("indicatorcolor", new MaterialMethodDefinition(Color.class, (this::setindicatorcolor_gwt_material_design_client_ui_materialtab)));
        this.widgets.put("materialtab", field);
    }

    private void addMaterialTabItem() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialTabItem", MaterialTabItem.class, true);
        this.widgets.put("materialtabitem", field);
    }

    private void addMaterialTextArea() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialTextArea", MaterialTextArea.class, true);
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("directionestimator", new MaterialMethodDefinition((boolean.class), (this::setdirectionestimator_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_com_google_gwt_user_client_ui_hasname)));
        field.getMethods().put("cursorpos", new MaterialMethodDefinition((int.class), (this::setcursorpos_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        field.getMethods().put("direction", new MaterialMethodDefinition(com.google.gwt.i18n.client.HasDirection.Direction.class, (this::setdirection_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("resizerule", new MaterialMethodDefinition(MaterialTextArea.ResizeRule.class, (this::setresizerule_gwt_material_design_client_ui_materialtextarea)));
        field.getMethods().put("length", new MaterialMethodDefinition((int.class), (this::setlength_gwt_material_design_client_base_hascounter)));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("type", new MaterialMethodDefinition(InputType.class, (this::settype_gwt_material_design_client_base_hasinputtype)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("alignment", new MaterialMethodDefinition(com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment.class, (this::setalignment_gwt_material_design_client_ui_materialvaluebox)));
        this.widgets.put("materialtextarea", field);
    }

    private void addMaterialTextBox() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialTextBox", MaterialTextBox.class, true);
        field.getMethods().put("maxlength", new MaterialMethodDefinition((int.class), (this::setmaxlength_gwt_material_design_client_ui_materialtextbox)));
        field.getMethods().put("iconcolor", new MaterialMethodDefinition(Color.class, (this::seticoncolor_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("directionestimator", new MaterialMethodDefinition((boolean.class), (this::setdirectionestimator_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("iconposition", new MaterialMethodDefinition(IconPosition.class, (this::seticonposition_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("iconprefix", new MaterialMethodDefinition((boolean.class), (this::seticonprefix_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("error", new MaterialMethodDefinition(String.class, (this::seterror_gwt_material_design_client_base_haserror)));
        field.getMethods().put("name", new MaterialMethodDefinition(String.class, (this::setname_com_google_gwt_user_client_ui_hasname)));
        field.getMethods().put("cursorpos", new MaterialMethodDefinition((int.class), (this::setcursorpos_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("success", new MaterialMethodDefinition(String.class, (this::setsuccess_gwt_material_design_client_base_haserror)));
        field.getMethods().put("direction", new MaterialMethodDefinition(com.google.gwt.i18n.client.HasDirection.Direction.class, (this::setdirection_gwt_material_design_client_ui_materialvaluebox)));
        field.getMethods().put("length", new MaterialMethodDefinition((int.class), (this::setlength_gwt_material_design_client_base_hascounter)));
        field.getMethods().put("active", new MaterialMethodDefinition((boolean.class), (this::setactive_gwt_material_design_client_base_hasactive)));
        field.getMethods().put("icontype", new MaterialMethodDefinition(IconType.class, (this::seticontype_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("visiblelength", new MaterialMethodDefinition((int.class), (this::setvisiblelength_gwt_material_design_client_ui_materialtextbox)));
        field.getMethods().put("validateonblur", new MaterialMethodDefinition((boolean.class), (this::setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators)));
        field.getMethods().put("text", new MaterialMethodDefinition(String.class, (this::settext_com_google_gwt_user_client_ui_hastext)));
        field.getMethods().put("readonly", new MaterialMethodDefinition((boolean.class), (this::setreadonly_gwt_material_design_client_base_hasreadonly)));
        field.getMethods().put("togglereadonly", new MaterialMethodDefinition((boolean.class), (this::settogglereadonly_gwt_material_design_client_base_hasreadonly)));
        BiConsumer<MaterialWidget, Boolean> setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker = (this::setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker);
        field.getMethods().put("allowblank", new MaterialMethodDefinition((boolean.class), setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker));
        field.getMethods().put("placeholder", new MaterialMethodDefinition(String.class, (this::setplaceholder_gwt_material_design_client_base_hasplaceholder)));
        field.getMethods().put("helpertext", new MaterialMethodDefinition(String.class, (this::sethelpertext_gwt_material_design_client_base_haserror)));
        field.getMethods().put("type", new MaterialMethodDefinition(InputType.class, (this::settype_gwt_material_design_client_base_hasinputtype)));
        field.getMethods().put("iconsize", new MaterialMethodDefinition(IconSize.class, (this::seticonsize_gwt_material_design_client_base_hasicon)));
        field.getMethods().put("alignment", new MaterialMethodDefinition(com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment.class, (this::setalignment_gwt_material_design_client_ui_materialvaluebox)));
        this.widgets.put("materialtextbox", field);
    }

    private void addMaterialTitle() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialTitle", MaterialTitle.class, true);
        field.getMethods().put("description", new MaterialMethodDefinition(String.class, (this::setdescription_gwt_material_design_client_base_hastitle)));
        field.getMethods().put("title", new MaterialMethodDefinition(String.class, (this::settitle_gwt_material_design_client_base_hastitle)));
        this.widgets.put("materialtitle", field);
    }

    private void addMaterialTopNav() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialTopNav", MaterialTopNav.class, true);
        this.widgets.put("materialtopnav", field);
    }

    private void addMaterialVideo() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialVideo", MaterialVideo.class, true);
        field.getMethods().put("url", new MaterialMethodDefinition(String.class, (this::seturl_gwt_material_design_client_ui_materialvideo)));
        this.widgets.put("materialvideo", field);
    }

    private void addMaterialWeather() {
        MaterialWidgetDefinition field = new MaterialWidgetDefinition("MaterialWeather", MaterialWeather.class, true);
        field.getMethods().put("location", new MaterialMethodDefinition(String.class, (this::setlocation_gwt_material_design_client_ui_materialweather)));
        field.getMethods().put("color", new MaterialMethodDefinition(String.class, (this::setcolor_gwt_material_design_client_ui_materialweather)));
        this.widgets.put("materialweather", field);
    }
        public native void setaccordion_gwt_material_design_client_ui_materialcollapsible(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialCollapsible::setAccordion(Z)(s);
}-*/;
    public native void setactivates_gwt_material_design_client_base_hasactivates(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasActivates::setActivates(Ljava/lang/String;)(s);
}-*/;
    public native void setactivator_gwt_material_design_client_ui_materialdropdown(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDropDown::setActivator(Ljava/lang/String;)(s);
}-*/;
    public native void setactive_gwt_material_design_client_base_hasactive(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasActive::setActive(Z)(s);
}-*/;
    public native void setactive_gwt_material_design_client_base_hasactiveparent(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasActiveParent::setActive(I)(s);
}-*/;
    public native void setalignment_gwt_material_design_client_ui_materialdropdown(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDropDown::setAlignment(Lgwt/material/design/client/constants/Alignment;)(s);
}-*/;
    public native void setalignment_gwt_material_design_client_ui_materialvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialValueBox::setAlignment(Lcom/google/gwt/user/client/ui/ValueBoxBase$TextAlignment;)(s);
}-*/;
    public void setallowblank_gwt_material_design_client_base_abstractvaluewidgetMaterialDatePicker(MaterialWidget x, boolean y){  ((gwt.material.design.client.base.AbstractValueWidget)x).setAllowBlank(y);       }
    public native void setallowbodyscroll_gwt_material_design_client_ui_materialsidenav(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSideNav::setAllowBodyScroll(Z)(s);
}-*/;
    public native void setalwaysshowactivator_gwt_material_design_client_ui_materialsidenav(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSideNav::setAlwaysShowActivator(Z)(s);
}-*/;
    public native void setautoclose_gwt_material_design_client_ui_materialdatepicker(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDatePicker::setAutoClose(Z)(s);
}-*/;
    public native void setavatar_gwt_material_design_client_base_hasavatar(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasAvatar::setAvatar(Z)(s);
}-*/;
    public native void setaxis_gwt_material_design_client_base_hasaxis(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasAxis::setAxis(Lgwt/material/design/client/constants/Axis;)(s);
}-*/;
    public native void setbackgroundcolor_gwt_material_design_client_base_hascolors(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasColors::setBackgroundColor(Lgwt/material/design/client/constants/Color;)(s);
}-*/;
    public native void setbeloworigin_gwt_material_design_client_ui_materialdropdown(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDropDown::setBelowOrigin(Z)(s);
}-*/;
    public native void setbottom_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setBottom(D)(s);
}-*/;
    public native void setcaption_gwt_material_design_client_base_hascaption(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasCaption::setCaption(Ljava/lang/String;)(s);
}-*/;
    public native void setcenteron_gwt_material_design_client_base_hascenteron(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasCenterOn::setCenterOn(Lgwt/material/design/client/constants/CenterOn;)(s);
}-*/;
    public native void setcircle_gwt_material_design_client_base_hascircle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasCircle::setCircle(Z)(s);
}-*/;
    public native void setcloseonclick_gwt_material_design_client_ui_materialsidenav(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSideNav::setCloseOnClick(Z)(s);
}-*/;
    public native void setcolor_gwt_material_design_client_ui_materialprogress(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialProgress::setColor(Lgwt/material/design/client/constants/Color;)(s);
}-*/;
    public native void setcolor_gwt_material_design_client_ui_materialspinner(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSpinner::setColor(Lgwt/material/design/client/constants/SpinnerColor;)(s);
}-*/;
    public native void setcolor_gwt_material_design_client_ui_materialweather(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialWeather::setColor(Ljava/lang/String;)(s);
}-*/;
    public native void setconstrainwidth_gwt_material_design_client_ui_materialdropdown(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDropDown::setConstrainWidth(Z)(s);
}-*/;
    public native void setcontainer_gwt_material_design_client_ui_materialdatepicker(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDatePicker::setContainer(Lgwt/material/design/client/constants/DatePickerContainer;)(s);
}-*/;
    public native void setcurrentpage_gwt_material_design_client_ui_materialpager(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialPager::setCurrentPage(I)(s);
}-*/;
    public native void setcursel_gwt_material_design_client_ui_materialsearch(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSearch::setCurSel(I)(s);
}-*/;
    public native void setcursorpos_gwt_material_design_client_ui_materialvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialValueBox::setCursorPos(I)(s);
}-*/;
    public native void setdateselectiontype_gwt_material_design_client_ui_materialdatepicker(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDatePicker::setDateSelectionType(Lgwt/material/design/client/ui/MaterialDatePicker$MaterialDatePickerType;)(s);
}-*/;
    public native void setdepth_gwt_material_design_client_base_hasdepth(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasDepth::setDepth(I)(s);
}-*/;
    public native void setdescription_gwt_material_design_client_base_hastitle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasTitle::setDescription(Ljava/lang/String;)(s);
}-*/;
    public native void setdetectorientation_gwt_material_design_client_ui_materialcard(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialCard::setDetectOrientation(Z)(s);
}-*/;
    public native void setdetectorientation_gwt_material_design_client_ui_materialdatepicker(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDatePicker::setDetectOrientation(Z)(s);
}-*/;
    public native void setdirection_gwt_material_design_client_ui_materialvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialValueBox::setDirection(Lcom/google/gwt/i18n/client/HasDirection$Direction;)(s);
}-*/;
    public native void setdirectionestimator_gwt_material_design_client_ui_materialvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialValueBox::setDirectionEstimator(Z)(s);
}-*/;
    public native void setdismissible_gwt_material_design_client_base_hasdismissible(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasDismissible::setDismissible(Z)(s);
}-*/;
    public native void setdisplay_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setDisplay(Lgwt/material/design/client/constants/Display;)(s);
}-*/;
    public native void setdisplay_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setDisplay(Lgwt/material/design/client/constants/Display;)(s);
}-*/;
    public native void setduration_gwt_material_design_client_base_hasdurationtransition(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasDurationTransition::setDuration(I)(s);
}-*/;
    public native void setedge_gwt_material_design_client_ui_materialsidenav(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSideNav::setEdge(Lgwt/material/design/client/constants/Edge;)(s);
}-*/;
    public native void setemptyplaceholder_gwt_material_design_client_ui_materiallistvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialListValueBox::setEmptyPlaceHolder(Ljava/lang/String;)(s);
}-*/;
    public native void setenabled_com_google_gwt_user_client_ui_hasenabled(Object x, Object s) /*-{
x.@com.google.gwt.user.client.ui.HasEnabled::setEnabled(Z)(s);
}-*/;
    public native void setenableindicator_gwt_material_design_client_ui_materialpager(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialPager::setEnableIndicator(Z)(s);
}-*/;
    public native void seterror_gwt_material_design_client_base_haserror(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasError::setError(Ljava/lang/String;)(s);
}-*/;
    public native void setexpandable_gwt_material_design_client_ui_materialsidenavmini(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSideNavMini::setExpandable(Z)(s);
}-*/;
    public native void setexpandonclick_gwt_material_design_client_ui_materialsidenavmini(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSideNavMini::setExpandOnClick(Z)(s);
}-*/;
    public native void setflex_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlex(Lgwt/material/design/client/constants/Flex;)(s);
}-*/;
    public native void setflexaligncontent_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlexAlignContent(Lgwt/material/design/client/constants/FlexAlignContent;)(s);
}-*/;
    public native void setflexalignself_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlexAlignSelf(Lgwt/material/design/client/constants/FlexAlignSelf;)(s);
}-*/;
    public native void setflexbasis_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlexBasis(Ljava/lang/String;)(s);
}-*/;
    public native void setflexdirection_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlexDirection(Lgwt/material/design/client/constants/FlexDirection;)(s);
}-*/;
    public native void setflexgrow_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlexGrow(Ljava/lang/Integer;)(s);
}-*/;
    public native void setflexorder_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlexOrder(Ljava/lang/Integer;)(s);
}-*/;
    public native void setflexshrink_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlexShrink(Ljava/lang/Integer;)(s);
}-*/;
    public native void setflexwrap_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setFlexWrap(Lgwt/material/design/client/constants/FlexWrap;)(s);
}-*/;
    public native void setfloat_gwt_material_design_client_base_hasfloat(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFloat::setFloat(Lcom/google/gwt/dom/client/Style$Float;)(s);
}-*/;
    public native void setfocus_com_google_gwt_user_client_ui_focusable(Object x, Object s) /*-{
x.@com.google.gwt.user.client.ui.Focusable::setFocus(Z)(s);
}-*/;
    public native void setfontsize_gwt_material_design_client_base_hasfontsize(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFontSize::setFontSize(Ljava/lang/String;)(s);
}-*/;
    public native void setfontweight_gwt_material_design_client_base_hasfontweight(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFontWeight::setFontWeight(Lcom/google/gwt/dom/client/Style$FontWeight;)(s);
}-*/;
    public native void setformat_gwt_material_design_client_ui_materialdatepicker(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDatePicker::setFormat(Ljava/lang/String;)(s);
}-*/;
    public native void setfullwidth_gwt_material_design_client_ui_materialslider(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSlider::setFullWidth(Z)(s);
}-*/;
    public native void setgrid_gwt_material_design_client_base_hasgrid(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasGrid::setGrid(Ljava/lang/String;)(s);
}-*/;
    public native void setgutter_gwt_material_design_client_ui_materialdropdown(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDropDown::setGutter(I)(s);
}-*/;
    public native void setgwtdisplay_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setGwtDisplay(Lcom/google/gwt/dom/client/Style$Display;)(s);
}-*/;
    public native void setheader_gwt_material_design_client_ui_materialcollection(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialCollection::setHeader(Ljava/lang/String;)(s);
}-*/;
    public void setheight_com_google_gwt_user_client_ui_uiobjectMaterialWidget(MaterialWidget x, String y){  ((com.google.gwt.user.client.ui.UIObject)x).setHeight(y);       }
    public native void setheight_gwt_material_design_client_ui_materialslider(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSlider::setHeight(Ljava/lang/String;)(s);
}-*/;
    public native void sethelpertext_gwt_material_design_client_base_haserror(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasError::setHelperText(Ljava/lang/String;)(s);
}-*/;
    public native void sethideon_gwt_material_design_client_base_hashideon(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasHideOn::setHideOn(Lgwt/material/design/client/constants/HideOn;)(s);
}-*/;
    public native void sethover_gwt_material_design_client_ui_materialdropdown(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDropDown::setHover(Z)(s);
}-*/;
    public native void sethoverable_gwt_material_design_client_base_hashoverable(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasHoverable::setHoverable(Z)(s);
}-*/;
    public native void sethref_gwt_material_design_client_base_hashref(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasHref::setHref(Ljava/lang/String;)(s);
}-*/;
    public native void seticoncolor_gwt_material_design_client_base_hasicon(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasIcon::setIconColor(Lgwt/material/design/client/constants/Color;)(s);
}-*/;
    public native void seticonposition_gwt_material_design_client_base_hasicon(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasIcon::setIconPosition(Lgwt/material/design/client/constants/IconPosition;)(s);
}-*/;
    public native void seticonprefix_gwt_material_design_client_base_hasicon(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasIcon::setIconPrefix(Z)(s);
}-*/;
    public native void seticonsize_gwt_material_design_client_base_hasicon(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasIcon::setIconSize(Lgwt/material/design/client/constants/IconSize;)(s);
}-*/;
    public native void seticontype_gwt_material_design_client_base_hasicon(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasIcon::setIconType(Lgwt/material/design/client/constants/IconType;)(s);
}-*/;
    public native void setindicatorcolor_gwt_material_design_client_ui_materialtab(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialTab::setIndicatorColor(Lgwt/material/design/client/constants/Color;)(s);
}-*/;
    public native void setindicatortemplate_gwt_material_design_client_ui_materialpager(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialPager::setIndicatorTemplate(Ljava/lang/String;)(s);
}-*/;
    public native void setinduration_gwt_material_design_client_base_hasinoutdurationtransition(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInOutDurationTransition::setInDuration(I)(s);
}-*/;
    public native void setinnertext_gwt_material_design_client_ui_materialicon(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialIcon::setInnerText(Ljava/lang/String;)(s);
}-*/;
    public native void setinterval_gwt_material_design_client_ui_materialslider(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSlider::setInterval(I)(s);
}-*/;
    public native void setlanguage_gwt_material_design_client_ui_materialdatepicker(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDatePicker::setLanguage(Lgwt/material/design/client/constants/DatePickerLanguage;)(s);
}-*/;
    public native void setlayoutposition_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setLayoutPosition(Lcom/google/gwt/dom/client/Style$Position;)(s);
}-*/;
    public native void setleft_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setLeft(D)(s);
}-*/;
    public native void setlength_gwt_material_design_client_base_hascounter(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasCounter::setLength(I)(s);
}-*/;
    public native void setletter_gwt_material_design_client_base_hasletter(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasLetter::setLetter(Ljava/lang/String;)(s);
}-*/;
    public native void setletterbackgroundcolor_gwt_material_design_client_base_hasletter(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasLetter::setLetterBackgroundColor(Lgwt/material/design/client/constants/Color;)(s);
}-*/;
    public native void setlettercolor_gwt_material_design_client_base_hasletter(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasLetter::setLetterColor(Lgwt/material/design/client/constants/Color;)(s);
}-*/;
    public native void setlineheight_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setLineHeight(D)(s);
}-*/;
    public native void setlocation_gwt_material_design_client_ui_materialweather(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialWeather::setLocation(Ljava/lang/String;)(s);
}-*/;
    public native void setmargin_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setMargin(D)(s);
}-*/;
    public native void setmarginbottom_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setMarginBottom(D)(s);
}-*/;
    public native void setmarginleft_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setMarginLeft(D)(s);
}-*/;
    public native void setmarginright_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setMarginRight(D)(s);
}-*/;
    public native void setmargintop_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setMarginTop(D)(s);
}-*/;
    public native void setmax_gwt_material_design_client_ui_materialinput(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialInput::setMax(Ljava/lang/String;)(s);
}-*/;
    public void setmax_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox(MaterialWidget x, String y){  ((gwt.material.design.client.ui.MaterialNumberBox)x).setMax(y);       }
    public native void setmax_gwt_material_design_client_ui_materialrange(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialRange::setMax(Ljava/lang/Integer;)(s);
}-*/;
    public native void setmaxlength_gwt_material_design_client_ui_materialtextbox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialTextBox::setMaxLength(I)(s);
}-*/;
    public native void setmaxpagelinksshown_gwt_material_design_client_ui_materialpager(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialPager::setMaxPageLinksShown(I)(s);
}-*/;
    public native void setmin_gwt_material_design_client_ui_materialinput(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialInput::setMin(Ljava/lang/String;)(s);
}-*/;
    public void setmin_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox(MaterialWidget x, String y){  ((gwt.material.design.client.ui.MaterialNumberBox)x).setMin(y);       }
    public native void setmin_gwt_material_design_client_ui_materialrange(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialRange::setMin(Ljava/lang/Integer;)(s);
}-*/;
    public native void setmultipleselect_gwt_material_design_client_ui_materiallistvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialListValueBox::setMultipleSelect(Z)(s);
}-*/;
    public native void setname_com_google_gwt_user_client_ui_hasname(Object x, Object s) /*-{
x.@com.google.gwt.user.client.ui.HasName::setName(Ljava/lang/String;)(s);
}-*/;
    public native void setname_gwt_material_design_client_ui_materiallistvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialListValueBox::setName(Ljava/lang/String;)(s);
}-*/;
    public native void setofflabel_gwt_material_design_client_ui_materialswitch(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSwitch::setOffLabel(Ljava/lang/String;)(s);
}-*/;
    public native void setoffset_gwt_material_design_client_base_hasgrid(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasGrid::setOffset(Ljava/lang/String;)(s);
}-*/;
    public native void setold_gwt_material_design_client_ui_materiallistvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialListValueBox::setOld(Z)(s);
}-*/;
    public native void setonlabel_gwt_material_design_client_ui_materialswitch(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSwitch::setOnLabel(Ljava/lang/String;)(s);
}-*/;
    public native void setopacity_gwt_material_design_client_base_hasopacity(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasOpacity::setOpacity(D)(s);
}-*/;
    public native void setorientation_gwt_material_design_client_base_hasorientation(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasOrientation::setOrientation(Lgwt/material/design/client/constants/Orientation;)(s);
}-*/;
    public native void setoutduration_gwt_material_design_client_base_hasinoutdurationtransition(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInOutDurationTransition::setOutDuration(I)(s);
}-*/;
    public native void setoverflow_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setOverflow(Lcom/google/gwt/dom/client/Style$Overflow;)(s);
}-*/;
    public native void setpadding_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setPadding(D)(s);
}-*/;
    public native void setpaddingbottom_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setPaddingBottom(D)(s);
}-*/;
    public native void setpaddingleft_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setPaddingLeft(D)(s);
}-*/;
    public native void setpaddingright_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setPaddingRight(D)(s);
}-*/;
    public native void setpaddingtop_gwt_material_design_client_base_hasinlinestyle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInlineStyle::setPaddingTop(D)(s);
}-*/;
    public native void setpagesize_gwt_material_design_client_ui_materialpager(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialPager::setPageSize(I)(s);
}-*/;
    public native void setpercent_gwt_material_design_client_base_hasprogress(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasProgress::setPercent(D)(s);
}-*/;
    public native void setpercent_gwt_material_design_client_ui_materialprogress(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialProgress::setPercent(D)(s);
}-*/;
    public native void setplaceholder_gwt_material_design_client_base_hasplaceholder(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasPlaceholder::setPlaceholder(Ljava/lang/String;)(s);
}-*/;
    public native void setposition_gwt_material_design_client_base_hasposition(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasPosition::setPosition(Lgwt/material/design/client/constants/Position;)(s);
}-*/;
    public native void setreadonly_gwt_material_design_client_base_hasreadonly(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasReadOnly::setReadOnly(Z)(s);
}-*/;
    public native void setrequired_gwt_material_design_client_ui_materialinput(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialInput::setRequired(Z)(s);
}-*/;
    public native void setresizerule_gwt_material_design_client_ui_materialtextarea(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialTextArea::setResizeRule(Lgwt/material/design/client/ui/MaterialTextArea$ResizeRule;)(s);
}-*/;
    public native void setright_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setRight(D)(s);
}-*/;
    public native void setselectedindex_gwt_material_design_client_ui_materiallistvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialListValueBox::setSelectedIndex(I)(s);
}-*/;
    public native void setselectedvalue_gwt_material_design_client_ui_materiallistvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialListValueBox::setSelectedValue(Ljava/lang/String;)(s);
}-*/;
    public native void setselectiontype_gwt_material_design_client_ui_materialdatepicker(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDatePicker::setSelectionType(Lgwt/material/design/client/ui/MaterialDatePicker$MaterialDatePickerType;)(s);
}-*/;
    public native void setseparator_gwt_material_design_client_base_hasseparator(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasSeparator::setSeparator(Z)(s);
}-*/;
    public native void setshadow_gwt_material_design_client_base_hasshadow(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasShadow::setShadow(I)(s);
}-*/;
    public native void setshowon_gwt_material_design_client_base_hasshowon(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasShowOn::setShowOn(Lgwt/material/design/client/constants/ShowOn;)(s);
}-*/;
    public native void setshowonattach_gwt_material_design_client_ui_materialsidenav(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSideNav::setShowOnAttach(Z)(s);
}-*/;
    public void setsize_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton(MaterialWidget x, ButtonSize y){  ((gwt.material.design.client.base.AbstractButton)x).setSize(y);       }
    public native void setsize_gwt_material_design_client_ui_materialpreloader(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialPreLoader::setSize(Lgwt/material/design/client/constants/LoaderSize;)(s);
}-*/;
    public void setstep_gwt_material_design_client_ui_materialnumberboxMaterialDoubleBox(MaterialWidget x, String y){  ((gwt.material.design.client.ui.MaterialNumberBox)x).setStep(y);       }
    public native void setstyle_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setStyle(Ljava/lang/String;)(s);
}-*/;
    public native void setsuccess_gwt_material_design_client_base_haserror(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasError::setSuccess(Ljava/lang/String;)(s);
}-*/;
    public native void settabindex_com_google_gwt_user_client_ui_focusable(Object x, Object s) /*-{
x.@com.google.gwt.user.client.ui.Focusable::setTabIndex(I)(s);
}-*/;
    public native void settarget_gwt_material_design_client_base_hashref(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasHref::setTarget(Ljava/lang/String;)(s);
}-*/;
    public native void settext_com_google_gwt_user_client_ui_hastext(Object x, Object s) /*-{
x.@com.google.gwt.user.client.ui.HasText::setText(Ljava/lang/String;)(s);
}-*/;
    public void settext_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton(MaterialWidget x, String y){  ((gwt.material.design.client.base.AbstractButton)x).setText(y);       }
    public native void settext_gwt_material_design_client_ui_materialchip(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialChip::setText(Ljava/lang/String;)(s);
}-*/;
    public native void settext_gwt_material_design_client_ui_materialnavbrand(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialNavBrand::setText(Ljava/lang/String;)(s);
}-*/;
    public native void settextalign_gwt_material_design_client_base_hastextalign(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasTextAlign::setTextAlign(Lgwt/material/design/client/constants/TextAlign;)(s);
}-*/;
    public native void settextcolor_gwt_material_design_client_base_hascolors(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasColors::setTextColor(Lgwt/material/design/client/constants/Color;)(s);
}-*/;
    public void settitle_com_google_gwt_user_client_ui_uiobjectMaterialWidget(MaterialWidget x, String y){  ((com.google.gwt.user.client.ui.UIObject)x).setTitle(y);       }
    public native void settitle_gwt_material_design_client_base_hastitle(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasTitle::setTitle(Ljava/lang/String;)(s);
}-*/;
    public native void settitle_gwt_material_design_client_ui_materiallistvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialListValueBox::setTitle(Ljava/lang/String;)(s);
}-*/;
    public native void settogglereadonly_gwt_material_design_client_base_hasreadonly(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasReadOnly::setToggleReadOnly(Z)(s);
}-*/;
    public native void settooltip_gwt_material_design_client_base_hastooltip(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasTooltip::setTooltip(Ljava/lang/String;)(s);
}-*/;
    public native void settooltipdelayms_gwt_material_design_client_base_hastooltip(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasTooltip::setTooltipDelayMs(I)(s);
}-*/;
    public native void settooltiphtml_gwt_material_design_client_base_hastooltip(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasTooltip::setTooltipHTML(Ljava/lang/String;)(s);
}-*/;
    public native void settooltipposition_gwt_material_design_client_base_hastooltip(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasTooltip::setTooltipPosition(Lgwt/material/design/client/constants/Position;)(s);
}-*/;
    public native void settop_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setTop(D)(s);
}-*/;
    public native void settotal_gwt_material_design_client_ui_materialpager(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialPager::setTotal(I)(s);
}-*/;
    public native void settruncate_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setTruncate(Z)(s);
}-*/;
    public void settype_gwt_material_design_client_base_abstractbuttonMaterialAnchorButton(MaterialWidget x, ButtonType y){  ((gwt.material.design.client.base.AbstractButton)x).setType(y);       }
    public native void settype_gwt_material_design_client_base_hasinputtype(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasInputType::setType(Lgwt/material/design/client/constants/InputType;)(s);
}-*/;
    public native void settype_gwt_material_design_client_ui_materialcollapsible(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialCollapsible::setType(Lgwt/material/design/client/constants/CollapsibleType;)(s);
}-*/;
    public native void settype_gwt_material_design_client_ui_materialcollectionitem(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialCollectionItem::setType(Lgwt/material/design/client/constants/CollectionType;)(s);
}-*/;
    public native void settype_gwt_material_design_client_ui_materialfab(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialFAB::setType(Lgwt/material/design/client/constants/FABType;)(s);
}-*/;
    public native void settype_gwt_material_design_client_ui_materialfooter(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialFooter::setType(Lgwt/material/design/client/constants/FooterType;)(s);
}-*/;
    public native void settype_gwt_material_design_client_ui_materialimage(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialImage::setType(Lgwt/material/design/client/constants/ImageType;)(s);
}-*/;
    public native void settype_gwt_material_design_client_ui_materialmodal(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialModal::setType(Lgwt/material/design/client/constants/ModalType;)(s);
}-*/;
    public native void settype_gwt_material_design_client_ui_materialprogress(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialProgress::setType(Lgwt/material/design/client/constants/ProgressType;)(s);
}-*/;
    public native void settype_gwt_material_design_client_ui_materialtab(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialTab::setType(Lgwt/material/design/client/constants/TabType;)(s);
}-*/;
    public native void seturl_gwt_material_design_client_base_hasimage(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasImage::setUrl(Ljava/lang/String;)(s);
}-*/;
    public native void seturl_gwt_material_design_client_ui_materialvideo(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialVideo::setUrl(Ljava/lang/String;)(s);
}-*/;
    public native void setvalidateonblur_gwt_material_design_client_base_validator_hasvalidators(Object x, Object s) /*-{
x.@gwt.material.design.client.base.validator.HasValidators::setValidateOnBlur(Z)(s);
}-*/;
    public native void setvalue_gwt_material_design_client_ui_materialswitch(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSwitch::setValue(Ljava/lang/Boolean;)(s);
}-*/;
    public native void setvisibility_gwt_material_design_client_base_materialwidget(Object x, Object s) /*-{
x.@gwt.material.design.client.base.MaterialWidget::setVisibility(Lcom/google/gwt/dom/client/Style$Visibility;)(s);
}-*/;
    public native void setvisible_com_google_gwt_user_client_ui_hasvisibility(Object x, Object s) /*-{
x.@com.google.gwt.user.client.ui.HasVisibility::setVisible(Z)(s);
}-*/;
    public native void setvisible_gwt_material_design_client_base_hasflexbox(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasFlexbox::setVisible(Z)(s);
}-*/;
    public native void setvisibleitemcount_gwt_material_design_client_ui_materiallistvaluebox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialListValueBox::setVisibleItemCount(I)(s);
}-*/;
    public native void setvisiblelength_gwt_material_design_client_ui_materialtextbox(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialTextBox::setVisibleLength(I)(s);
}-*/;
    public native void setwaves_gwt_material_design_client_base_haswaves(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasWaves::setWaves(Lgwt/material/design/client/constants/WavesType;)(s);
}-*/;
    public void setwidth_com_google_gwt_user_client_ui_uiobjectMaterialWidget(MaterialWidget x, String y){  ((com.google.gwt.user.client.ui.UIObject)x).setWidth(y);       }
    public native void setwidth_gwt_material_design_client_ui_materialsidenav(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialSideNav::setWidth(Ljava/lang/String;)(s);
}-*/;
    public native void setwithheader_gwt_material_design_client_base_haswithheader(Object x, Object s) /*-{
x.@gwt.material.design.client.base.HasWithHeader::setWithHeader(Z)(s);
}-*/;
    public native void setyearstodisplay_gwt_material_design_client_ui_materialdatepicker(Object x, Object s) /*-{
x.@gwt.material.design.client.ui.MaterialDatePicker::setYearsToDisplay(I)(s);
}-*/;

}
