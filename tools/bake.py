from PIL import Image, ImageChops
from pathlib import Path

rooot = Path(__file__).parent
dusts = rooot / "dusts"
masks = rooot / "masks"
palet = rooot / "palet"
corks = rooot / "corks"
shape = sorted((rooot/"botle").glob("*.png"))
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

key = Image.open(palet / "glass.png").convert("RGBA")
key = [key.getpixel((x, 0)) for x in range(key.width)]

def strip_rows(tier):
    img = Image.open(tier).convert("RGBA")
    if img.width != len(key):
        raise ValueError(f"{tier.name} is {img.width} wide, whereas glass.png is {len(key)}")
    if (img.height not in (1,2)):
        raise ValueError(f"{tier.name} has {img.height} rows. 1 is for a palette swap, 2 is for a palette blend.")
    return [dict(zip(key, (img.getpixel((x,y)) for x in range(img.width)))) for y in range(img.height)]

def swap(src, pal):
    out = Image.new("RGBA", src.size)
    out.putdata([pal[p] if p[3] else (0,0,0,0) for p in src.get_flattened_data()])
    return out

def mix(a,b,t):
    return tuple(round(a[i] * t + b[i] * (1-t)) for i in range(4))

def blend(src, hi_pal, lo_pal):
    hi, lo = swap(src, hi_pal), swap(src, lo_pal)
    body = [(x,y) for y in range(src.height) for x in range(src.width) if hi.getpixel((x,y))[3] or lo.getpixel((x,y))[3]]
    rows = [y for _, y in body]
    top, bottom = min(rows) + 1, max(rows) -1
    out = Image.new("RGBA", src.size)
    for x,y in body:
        t =1 if bottom <= top else min(1, max(0,(bottom-y)/(bottom-top)))
        out.putpixel((x,y),mix(hi.getpixel((x,y)),lo.getpixel((x,y)),t))
    return out

for sheep in shape:
    src = Image.open(sheep).convert("RGBA")
    stray = {p for p in src.get_flattened_data() if p[3] and p not in key}
    if stray:
        raise ValueError(f"bottles/{sheep.name} uses colors not in glass.png: {stray}")

for tier in sorted(palet.glob("*.png")):
    rows = strip_rows(tier)
    if not rows: continue
    overlays = corks/tier.stem
    for sheep in shape:
        cork = overlays / sheep.name
        if overlays.is_dir() and not cork.exists(): continue
        src = Image.open(sheep).convert("RGBA")
        if len(rows) == 1:
            out = swap(src,rows[0])
        else:
            out = blend(src,rows[0], rows[1])
        if overlays.is_dir(): out.alpha_composite(Image.open(cork).convert("RGBA"))
        holes = [(x,y) for y in range(src.height) for x in range(src.width) if src.getpixel((x,y))[3] and not out.getpixel((x,y))[3]]
        if holes:
            raise ValueError(f"b_{sheep.stem}_{tier.stem} has holes at {holes}")
        out.save(baked/f"b_{sheep.stem}_{tier.stem}.png")