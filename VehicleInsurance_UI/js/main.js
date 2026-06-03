// Shared JS for all pages
document.addEventListener("DOMContentLoaded", function() {
  // Highlight active nav, add generic utilities
  console.log("AutoInsure Prototype Loaded");
});

// Common function to show status colors
function getStatusBadge(status) {
  const map = {
    "proposal submitted": "status-proposal",
    "quote generated": "status-quote",
    "active": "status-active",
    "expired": "status-expired"
  };
  return map[status.toLowerCase()] || "status-proposal";
}