import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CoverageEligibilityRequest from './coverage-eligibility-request';
import CoverageEligibilityRequestDetail from './coverage-eligibility-request-detail';
import CoverageEligibilityRequestUpdate from './coverage-eligibility-request-update';
import CoverageEligibilityRequestDeleteDialog from './coverage-eligibility-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CoverageEligibilityRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CoverageEligibilityRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CoverageEligibilityRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={CoverageEligibilityRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CoverageEligibilityRequestDeleteDialog} />
  </>
);

export default Routes;
