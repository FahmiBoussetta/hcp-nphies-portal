import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './operation-outcome.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOperationOutcomeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OperationOutcomeDetail = (props: IOperationOutcomeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { operationOutcomeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="operationOutcomeDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.operationOutcome.detail.title">OperationOutcome</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{operationOutcomeEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="hcpNphiesPortalApp.operationOutcome.value">Value</Translate>
            </span>
          </dt>
          <dd>{operationOutcomeEntity.value}</dd>
          <dt>
            <span id="system">
              <Translate contentKey="hcpNphiesPortalApp.operationOutcome.system">System</Translate>
            </span>
          </dt>
          <dd>{operationOutcomeEntity.system}</dd>
          <dt>
            <span id="parsed">
              <Translate contentKey="hcpNphiesPortalApp.operationOutcome.parsed">Parsed</Translate>
            </span>
          </dt>
          <dd>{operationOutcomeEntity.parsed}</dd>
        </dl>
        <Button tag={Link} to="/operation-outcome" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/operation-outcome/${operationOutcomeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ operationOutcome }: IRootState) => ({
  operationOutcomeEntity: operationOutcome.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OperationOutcomeDetail);
