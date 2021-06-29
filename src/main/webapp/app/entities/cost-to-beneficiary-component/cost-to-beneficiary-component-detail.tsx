import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cost-to-beneficiary-component.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CostToBeneficiaryComponentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const costToBeneficiaryComponentEntity = useAppSelector(state => state.costToBeneficiaryComponent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="costToBeneficiaryComponentDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.detail.title">CostToBeneficiaryComponent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{costToBeneficiaryComponentEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.type">Type</Translate>
            </span>
          </dt>
          <dd>{costToBeneficiaryComponentEntity.type}</dd>
          <dt>
            <span id="isMoney">
              <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.isMoney">Is Money</Translate>
            </span>
          </dt>
          <dd>{costToBeneficiaryComponentEntity.isMoney ? 'true' : 'false'}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.value">Value</Translate>
            </span>
          </dt>
          <dd>{costToBeneficiaryComponentEntity.value}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.coverage">Coverage</Translate>
          </dt>
          <dd>{costToBeneficiaryComponentEntity.coverage ? costToBeneficiaryComponentEntity.coverage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cost-to-beneficiary-component" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cost-to-beneficiary-component/${costToBeneficiaryComponentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CostToBeneficiaryComponentDetail;
