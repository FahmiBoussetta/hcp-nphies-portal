import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Coverage from './coverage';
import CoverageDetail from './coverage-detail';
import CoverageUpdate from './coverage-update';
import CoverageDeleteDialog from './coverage-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CoverageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CoverageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CoverageDetail} />
      <ErrorBoundaryRoute path={match.url} component={Coverage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CoverageDeleteDialog} />
  </>
);

export default Routes;
