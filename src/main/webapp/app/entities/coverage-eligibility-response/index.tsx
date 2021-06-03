import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CoverageEligibilityResponse from './coverage-eligibility-response';
import CoverageEligibilityResponseDetail from './coverage-eligibility-response-detail';
import CoverageEligibilityResponseUpdate from './coverage-eligibility-response-update';
import CoverageEligibilityResponseDeleteDialog from './coverage-eligibility-response-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CoverageEligibilityResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CoverageEligibilityResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CoverageEligibilityResponseDetail} />
      <ErrorBoundaryRoute path={match.url} component={CoverageEligibilityResponse} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CoverageEligibilityResponseDeleteDialog} />
  </>
);

export default Routes;
