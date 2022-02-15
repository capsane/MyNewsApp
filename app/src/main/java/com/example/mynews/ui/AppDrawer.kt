package com.example.mynews.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynews.R
import com.example.mynews.ui.components.JetnewsIcon
import com.example.mynews.ui.components.NavigationIcon
import com.example.mynews.ui.theme.JetnewsTheme

/**
 * @Description:
 *
 * @author zhuoxinwang
 * @version v13.5.0
 * @since 2022/2/15
 */

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToInterests: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        JetNewsLogo()
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))

        DrawerButton(icon = Icons.Filled.Home, label = stringResource(id = R.string.home_title),
            isSelected = currentRoute == JetnewsDestinations.HOME_ROUTE,
            action = {
                navigateToHome()
                closeDrawer()
            }
        )

        DrawerButton(
            icon = Icons.Filled.ListAlt,
            label = stringResource(id = R.string.interests_title),
            isSelected = currentRoute == JetnewsDestinations.INTERESTS_ROUTE,
            action = {
                navigateToInterests()
                closeDrawer()
            }
        )

    }
}

@Composable
private fun JetNewsLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        JetnewsIcon()
        Spacer(Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.ic_jetnews_wordmark),
            contentDescription = stringResource(R.string.app_name),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
        )
    }
}

@Composable
private fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationIcon(
                    icon = icon,
                    isSelected = isSelected,
                    contentDescription = null, // decorative
                    tintColor = textIconColor
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    JetnewsTheme {
        Surface {
            AppDrawer(
                currentRoute = JetnewsDestinations.HOME_ROUTE,
                navigateToHome = {},
                navigateToInterests = {},
                closeDrawer = { }
            )
        }
    }
}