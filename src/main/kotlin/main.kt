import org.apache.commons.compress.archivers.ArchiveEntry
import org.apache.commons.compress.archivers.zip.Zip64Mode
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.RandomAccessFile

fun main() {
    RandomAccessFile("archive/input.bin", "rw").use { it.setLength(1024L*1024*1024*5) }

    val outputFile = File("archive/output.zip").also { it.createNewFile() }
    ZipArchiveOutputStream(BufferedOutputStream(outputFile.outputStream())).use { output ->
        output.setUseZip64(Zip64Mode.Always)
        output.setCreateUnicodeExtraFields(ZipArchiveOutputStream.UnicodeExtraFieldPolicy.ALWAYS)

        output.putArchiveEntry(ZipArchiveEntry("input.bin"))

        FileInputStream("archive/input.bin").use {
            it.transferTo(output)
        }

        output.closeArchiveEntry()
    }
}