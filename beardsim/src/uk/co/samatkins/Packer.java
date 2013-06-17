package uk.co.samatkins;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
public class Packer {
        public static void main (String[] args) throws Exception {
                TexturePacker2.process("../beardsim-android/assets/unpacked", "../beardsim-android/assets", "packed");
        }
}