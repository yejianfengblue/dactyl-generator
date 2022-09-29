package com.example.dactylgeneratordemo.manuform;

import com.example.dactylgeneratordemo.OpenscadService.Camera;
import com.example.dactylgeneratordemo.SwitchType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "manuform.url=http://localhost:3030/api/manuform",
        "manuform.generate-png=true"
})
public class ManuformDemoTest {

    final String MANUFORM_BASE_FILENAME = "manuform/manuform-4x6+6";

    @Autowired
    ManuformService manuformService;

    private Manuform getBaseManuform() {

        Manuform.Keys keys = new Manuform.Keys(6, 4, ThumbCount.SIX,
                                               LastRow.FULL, InnerColumn.NORMIE, false,
                                               SwitchType.BOX);
        Manuform.Curve curve = new Manuform.Curve(12, 12, 36, 4, 15, 180);
        Manuform.Connector connector = new Manuform.Connector(false, ConnectorType.RJ9, false);
        Manuform.Form form = new Manuform.Form(false,
                                               6, -3, 7,
                                               true,
                                               0, 0,
                                               2.8, -6.5,
                                               0, 0,
                                               -13, 6,
                                               false,
                                               20,
                                               7, 3,
                                               false,
                                               false);
        Manuform.Misc misc = new Manuform.Misc(false, true);

        return new Manuform(keys, curve, connector, form, misc);
    }

    @Test
    void base() {

        Manuform manuform = getBaseManuform();
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME,
                                 Camera.DIAGONAL, Camera.TOP, Camera.BACK_TOP,
                                 Camera.RIGHT,
                                 Camera.BOTTOM, Camera.BOTTOM_DIST_200);
    }

    @Test
    void thumbCount() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getKeys().setThumbCount(ThumbCount.TWO);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME.replace("+6", "+2"), camera);
        manuform.getKeys().setThumbCount(ThumbCount.THREE);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME.replace("+6", "+3"), camera);
        manuform.getKeys().setThumbCount(ThumbCount.THREE_MINI);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME.replace("+6", "+3mini"), camera);
        manuform.getKeys().setThumbCount(ThumbCount.FOUR);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME.replace("+6", "+4"), camera);
        manuform.getKeys().setThumbCount(ThumbCount.FIVE);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME.replace("+6", "+5"), camera);
    }

    @Test
    void lastRow() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getKeys().setLastRow(LastRow.ZERO);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.last-row=0)", camera);
        manuform.getKeys().setLastRow(LastRow.TWO);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.last-row=2)", camera);
    }

    @Test
    void innerColumn() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getKeys().setInnerColumn(InnerColumn.OUTIE);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.inner-column=ergodox)", camera);
        manuform.getKeys().setInnerColumn(InnerColumn.INNIE);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.inner-column=without)", camera);
    }

    @Test
    void hideLastPinky() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getKeys().setHideLastPinky(true);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.hide-last-pinky=yes)", camera);
    }

    @Test
    void switchType() {

        Camera camera = Camera.BOTTOM_DIST_200;
        Manuform manuform = getBaseManuform();
        manuform.getKeys().setSwitchType(SwitchType.MX);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.switch-type=mx)", camera);
        manuform.getKeys().setSwitchType(SwitchType.MX_SNAP_IN);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.switch-type=mx-snap-in)", camera);
        manuform.getKeys().setSwitchType(SwitchType.ALPS);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.switch-type=alps)", camera);
        manuform.getKeys().setSwitchType(SwitchType.CHOC);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.switch-type=choc)", camera);
        manuform.getKeys().setSwitchType(SwitchType.KAILH);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(keys.switch-type=kailh)", camera);
    }

    @Test
    void columnCurvature() {

        Manuform manuform = getBaseManuform();
        manuform.getCurve().setColumnCurvature(6);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.column-curvature=pi_6)",
                                 Camera.DIAGONAL, Camera.RIGHT);
    }

    @Test
    void pinkyColumnCurvature() {

        Manuform manuform = getBaseManuform();
        manuform.getCurve().setPinkyColumnCurvature(6);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.pinky-column-curvature=pi_6)",
                                 Camera.DIAGONAL, Camera.RIGHT);
    }

    @Test
    void rowCurvature() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getCurve().setRowCurvature(18);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.row-curvature=pi_18)", camera);
    }

    @Test
    void centerCol() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getCurve().setCenterCol(1);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.centercol=index)", camera);
        manuform.getCurve().setCenterCol(2);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.centercol=middle)", camera);
        manuform.getCurve().setCenterCol(3);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.centercol=ring)", camera);
    }

    @Test
    void tenting() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getCurve().setTenting(6);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.tenting=6)", camera);
    }

    @Test
    void rotateX() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        // manuform.getCurve().setRotateX(36);
        // manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.rotate-x=pi_36)", camera);
        manuform.getCurve().setRotateX(15);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.rotate-x=pi_15)", camera);
        // manuform.getCurve().setRotateX(10);
        // manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(curve.rotate-x=pi_10)", camera);
    }

    @Test
    void connector() {

        Camera camera = Camera.BACK_TOP;
        Manuform manuform = getBaseManuform();

        manuform.getConnector().setType(ConnectorType.TRRS);
        manuform.getConnector().setMicrousb(true);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(connector.type=trrs)", camera);

        manuform.getConnector().setType(ConnectorType.NONE);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(connector.type=none)", camera);

        manuform.getConnector().setExternal(true);
        manuform.getConnector().setType(ConnectorType.NONE);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(connector.external=yes)", camera);
    }

    @Test
    void hotswap() {

        Camera camera = Camera.BOTTOM_DIST_200;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setHotswap(true);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.hotswap=yes)", camera);
    }

    @Test
    void thumbClusterOffsetX() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setThumbClusterOffsetX(-10);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.thumb-cluster-offset-x=-10)", camera);
    }

    @Test
    void thumbClusterOffsetY() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setThumbClusterOffsetY(-23);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.thumb-cluster-offset-y=-23)", camera);
    }

    @Test
    void thumbClusterOffsetZ() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setThumbClusterOffsetZ(27);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.thumb-cluster-offset-z=27)", camera);
    }

    @Test
    void stagger() {

        Manuform manuform = getBaseManuform();
        manuform.getForm().setStagger(false);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger=no)",
                                 Camera.DIAGONAL, Camera.TOP);
    }

    @Test
    void staggerIndexY() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setStaggerIndexY(10);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger-index-y=10)", camera);
    }

    @Test
    void staggerIndexZ() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setStaggerIndexZ(15);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger-index-z=15)", camera);
    }

    @Test
    void staggerMiddleY() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setStaggerMiddleY(10);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger-middle-y=10)", camera);
    }

    @Test
    void staggerMiddleZ() {

        Camera camera = Camera.BACK_TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setStaggerMiddleZ(10);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger-middle-z=10)", camera);
    }

    @Test
    void staggerRingY() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setStaggerRingY(10);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger-ring-y=10)", camera);
    }

    @Test
    void staggerRingZ() {

        Camera camera = Camera.BACK_TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setStaggerRingZ(15);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger-ring-z=15)", camera);
    }

    @Test
    void staggerPinkyY() {

        Camera camera = Camera.TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setStaggerPinkyY(0);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger-pinky-y=0)", camera);
    }

    @Test
    void staggerPinkyZ() {

        Camera camera = Camera.BACK_TOP;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setStaggerPinkyZ(26);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.stagger-pinky-z=26)", camera);
    }

    @Test
    void widePinky() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setWidePinky(true);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.wide-pinky=yes)", camera);
    }

    @Test
    void heightOffset() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setHeightOffset(4);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.height-offset=4)", camera);
    }

    @Test
    void webThickness() {

        Camera camera = Camera.BOTTOM_DIST_200;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setWebThickness(20);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.web-thickness=20)", camera);
    }

    @Test
    void wallThickness() {

        Camera camera = Camera.BOTTOM;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setWallThickness(1);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.wall-thickness=1)", camera);
    }

    @Test
    void wirePost() {

        Camera camera = Camera.BOTTOM;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setWirePost(true);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.wire-post=yes)", camera);
    }

    @Test
    void screwInserts() {

        Camera camera = Camera.BOTTOM;
        Manuform manuform = getBaseManuform();
        manuform.getForm().setScrewInserts(true);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(form.screw-inserts=yes)", camera);
    }

    @Test
    void keycaps() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getMisc().setKeycaps(true);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(misc.keycaps=yes)", camera);
    }

    @Test
    void leftSide() {

        Camera camera = Camera.DIAGONAL;
        Manuform manuform = getBaseManuform();
        manuform.getMisc().setRightSide(false);
        manuformService.generate(manuform, MANUFORM_BASE_FILENAME + "-(misc.left-side)", camera);
    }

}
