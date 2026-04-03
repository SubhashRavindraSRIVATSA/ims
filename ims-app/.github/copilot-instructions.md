# Copilot Instructions for IMS App

## Project Overview
This is an Angular 19 standalone application for an Inventory Management System (IMS). It manages products, suppliers, categories, purchases, sales, and transactions with role-based access (admin vs user).

## Architecture
- **Frontend**: Angular 19 with standalone components
- **Backend**: REST API at `http://localhost:8080/api` (external, not in this repo)
- **Authentication**: JWT tokens stored encrypted in localStorage using CryptoJS
- **Routing**: Protected routes with `GuardService` checking auth and admin roles
- **Charts**: Uses `@swimlane/ngx-charts` for data visualization

## Key Components & Services
- `ApiService`: Centralized HTTP client with encrypted localStorage for tokens/roles
- `GuardService`: Route guards for authentication and admin access
- Components: Dashboard, Product, Supplier, Category, Transaction, etc. (many are placeholder implementations)

## Developer Workflows
- **Start dev server**: `npm start` or `ng serve` (runs on http://localhost:4200)
- **Build**: `npm run build` or `ng build` (outputs to `dist/ims-app`)
- **Test**: `npm test` or `ng test` (Karma/Jasmine)
- **Watch mode**: `npm run watch` or `ng build --watch --configuration development`

## Coding Patterns
- **Component prefix**: `app-` (e.g., `<app-product>`)
- **Standalone components**: Use `imports: []` in `@Component` decorators
- **API calls**: Always include auth headers via `ApiService.getHeader()`
- **Encryption**: Use `ApiService.encryptAndSaveToStorage()` for sensitive data in localStorage
- **Route protection**: Add `canActivate: [GuardService]` and `data: {requiresAdmin: true}` for admin routes
- **Error handling**: Check `ApiService.isAuthenticated()` and `isAdmin()` before API calls

## Data Flow
- Login/Register → JWT token encrypted in localStorage
- API requests include `Authorization: Bearer {token}` header
- Admin routes require `role === "ADMIN"` (also encrypted in localStorage)
- Transactions: Purchase/sell operations update inventory via backend

## Common Tasks
- Adding new entity: Create component, add routes in `app.routes.ts`, implement CRUD in `ApiService`
- New chart: Import from `ngx-charts` in component's `imports`
- Authentication check: Use `ApiService.isAuthenticated()` in components

## File Structure Highlights
- `src/app/service/api.service.ts`: All backend communication
- `src/app/app.routes.ts`: Route definitions with guards
- `src/app/service/guard.service.ts`: Authentication logic
- Components in `src/app/{feature}/`: Each feature has .ts, .html, .css, .spec.ts</content>
<parameter name="filePath">d:\Projects\Personal projects\ims\ims-app\.github\copilot-instructions.md