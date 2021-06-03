import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './insurance-benefit.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInsuranceBenefitDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InsuranceBenefitDetail = (props: IInsuranceBenefitDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { insuranceBenefitEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="insuranceBenefitDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.detail.title">InsuranceBenefit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{insuranceBenefitEntity.id}</dd>
          <dt>
            <span id="allowed">
              <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.allowed">Allowed</Translate>
            </span>
          </dt>
          <dd>{insuranceBenefitEntity.allowed}</dd>
          <dt>
            <span id="used">
              <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.used">Used</Translate>
            </span>
          </dt>
          <dd>{insuranceBenefitEntity.used}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.responseInsuranceItem">Response Insurance Item</Translate>
          </dt>
          <dd>{insuranceBenefitEntity.responseInsuranceItem ? insuranceBenefitEntity.responseInsuranceItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/insurance-benefit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/insurance-benefit/${insuranceBenefitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ insuranceBenefit }: IRootState) => ({
  insuranceBenefitEntity: insuranceBenefit.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InsuranceBenefitDetail);
