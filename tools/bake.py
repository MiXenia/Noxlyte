from PIL import Image, ImageChops
from pathlib import Path

rooot = Path(__file__).parent
dusts = rooot / "dusts"
masks = rooot / "masks"
baked = rooot / "../src/main/resources/assets/noxlyte/textures/item"
sg = Image.open(rooot / "shatterglass.png")

for m in masks.glob("*.png"):
    mc = Image.open(m)
    ms, ml, mr = mc.convert("RGB").split()
    so = sg.convert("RGBA")
    sa = so.getchannel("A")
    sa = ImageChops.multiply(sa,ms)
    so.putalpha(sa)
    so.save(baked/f"s_{m.stem}_shatterglass.png")
    for d in dusts.glob("*.png"):
        dc = Image.open(d)
        dc = dc.convert("RGBA")
        dl = dc.copy()
        dr = dc.copy()
        da = dc.getchannel("A")
        la = ImageChops.multiply(da,ml)
        ra = ImageChops.multiply(da,mr)
        dl.putalpha(la)
        dr.putalpha(ra)
        dl.save(baked/f"l_{m.stem}_{d.stem}.png")
        dr.save(baked/f"r_{m.stem}_{d.stem}.png")
