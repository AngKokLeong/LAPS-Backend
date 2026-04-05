# Leave Management System - Static HTML Pages

This folder contains static HTML snapshots of all pages in the Leave Management System application.

## Overview

These are self-contained HTML files that represent the various views and functionality within the Leave Management System. All pages use Tailwind CSS via CDN for styling and include mock data to showcase the interface.

## Files Included

### 📋 Index
- **index.html** - Landing page with links to all other pages

### 🔐 Public Pages
- **login.html** - Authentication page with demo account credentials

### 👤 Employee Pages
- **dashboard.html** - Employee dashboard with leave statistics and recent requests
- **apply-for-leave.html** - Form to submit new leave requests
- **view-leaves.html** - List of all leave requests with filtering

### 👔 Manager Pages  
- **leave-approvals.html** - Manager view to approve/reject team leave requests
- **team-leaves.html** - Overview of all team members' leave schedules
- **team-leave-history.html** - View all reviewed leave requests from your team
- **view-team-members-leave.html** - View leave balances for your team members
- **movement-register.html** - Track employee movements and attendance

### ⚙️ Administrator Pages
- **employee-management.html** - Manage employee accounts, roles, and details
- **leave-type-management.html** - Configure leave types with status toggle switches
- **leave-entitlement.html** - Manage employee leave entitlements and balances
- **email-template-management.html** - Manage system email templates with CRUD operations
- **email-template.html** - Preview different email notification templates (demonstration)
- **email-templates.html** - (Legacy) Email template management page

## Features

### Technology Stack
- **HTML5** - Semantic markup
- **Tailwind CSS** (via CDN) - Utility-first styling
- **SVG Icons** - Inline SVG for icons
- **Responsive Design** - Mobile-friendly layouts

### UI Components
- Navigation sidebar with role-based sections
- Data tables with pagination
- Filter and search functionality
- Status badges and indicators
- Interactive cards and modals
- Progress bars for leave balances
- Toggle switches for status management
- Action buttons (Edit, Delete, View, Approve, Reject)

### Design System
- **Colors**: Blue (primary), Green (success), Red (error), Yellow (warning), Purple/Orange/etc. (categorical)
- **Typography**: Clean, readable font hierarchy
- **Spacing**: Consistent padding and margins
- **Borders**: Subtle borders and rounded corners
- **Shadows**: Hover effects and elevation

## Mock Data

All pages include representative mock data to demonstrate:
- Employee information and roles
- Leave requests with various statuses
- Leave type configurations
- Entitlement balances
- Email template content
- Movement register entries

## User Roles

The system supports three user roles with different access levels:

1. **Employee** (john@company.com)
   - View own dashboard
   - Apply for leave
   - View own leave history

2. **Manager** (sarah@company.com)
   - All employee features
   - Approve/reject team leave requests
   - View team leave schedules
   - Access movement register

3. **Administrator** (admin@company.com)
   - All manager features
   - Manage employees
   - Configure leave types
   - Manage entitlements
   - Manage email templates

## Usage

Simply open any HTML file in a web browser to view the static snapshot. All pages are self-contained and don't require a server or build process.

### Getting Started
1. Open `index.html` in your browser
2. Navigate to any page using the links provided
3. Explore the different views and functionality

## Browser Compatibility

These pages work in all modern browsers:
- Chrome/Edge (latest)
- Firefox (latest)
- Safari (latest)

## Notes

- These are **static snapshots** - no interactivity or data persistence
- Forms and buttons are non-functional (for display only)
- Tailwind CSS is loaded from CDN (requires internet connection)
- No backend or database required
- Perfect for documentation, design review, or reference

## Generated On

April 3, 2026

## Pages Count

**15 Total Pages** covering all major features and user roles in the Leave Management System.