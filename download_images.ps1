# Script para descargar imágenes de productos
$outputPath = "c:\Users\damasa\Desktop\Nostrum-1\Nostrum\Frontend\imagenes"

# Crear directorio si no existe
if (!(Test-Path $outputPath)) {
    New-Item -ItemType Directory -Path $outputPath -Force
}

# Array de imágenes a descargar (usando placeholder.com)
$images = @(
    @{name="RatonLogitech.png"; url="https://via.placeholder.com/400x400/1a1a2e/00b3ff?text=Logitech+G502"},
    @{name="RatonRazer.png"; url="https://via.placeholder.com/400x400/1a1a2e/00ff00?text=Razer+DeathAdder"},
    @{name="TecladoSteelseries.png"; url="https://via.placeholder.com/400x400/1a1a2e/ff6b6b?text=HyperX+Alloy"},
    @{name="TechadoRazer.png"; url="https://via.placeholder.com/400x400/1a1a2e/00ff00?text=Razer+Huntsman"},
    @{name="HeadsetHyperX.png"; url="https://via.placeholder.com/400x400/1a1a2e/ff0000?text=HyperX+Cloud"},
    @{name="CascosSteelseries.png"; url="https://via.placeholder.com/400x400/1a1a2e/ff6b6b?text=SteelSeries+Arctis"},
    @{name="RTX4070.png"; url="https://via.placeholder.com/400x400/1a1a2e/76b900?text=RTX+4070"},
    @{name="ssd.png"; url="https://via.placeholder.com/400x400/1a1a2e/0066cc?text=SSD+NVMe"},
    @{name="FunkopopWitcher.png"; url="https://via.placeholder.com/400x400/1a1a2e/ffcc00?text=Funko+Witcher"},
    @{name="FunkopopEldenring.png"; url="https://via.placeholder.com/400x400/1a1a2e/ffcc00?text=Funko+Elden+Ring"}
)

Write-Host "Descargando imágenes..." -ForegroundColor Green

foreach ($img in $images) {
    $filePath = Join-Path $outputPath $img.name
    try {
        Write-Host "Descargando $($img.name)..." -ForegroundColor Yellow
        Invoke-WebRequest -Uri $img.url -OutFile $filePath -ErrorAction Stop
        Write-Host "  ✓ $($img.name) descargada" -ForegroundColor Green
    } catch {
        Write-Host "  ✗ Error descargando $($img.name): $_" -ForegroundColor Red
    }
}

Write-Host "`nProceso completado. Imágenes guardadas en: $outputPath" -ForegroundColor Cyan
